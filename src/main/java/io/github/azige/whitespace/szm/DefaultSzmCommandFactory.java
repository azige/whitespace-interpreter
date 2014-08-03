/*
 * Copyright 2014 Azige.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.azige.whitespace.szm;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import io.github.azige.whitespace.command.AbstractCommand;
import io.github.azige.whitespace.command.Command;
import io.github.azige.whitespace.command.CommandType;
import io.github.azige.whitespace.command.DefaultCommandFactory;
import io.github.azige.whitespace.command.ExecutableCommand;
import io.github.azige.whitespace.command.ExecutableParameterCommand;
import io.github.azige.whitespace.command.LabelCommand;
import io.github.azige.whitespace.command.SimpleCommand;
import io.github.azige.whitespace.command.SimpleParameterCommand;
import io.github.azige.whitespace.vm.WhitespaceVM;

public class DefaultSzmCommandFactory extends DefaultCommandFactory implements SzmCommandFactory{

    static final long SZM_INT_MAX_VALUE = 0x7fffffff;

    /**
     * 存储程序退出状态码的变量的地址。
     */
    static final BigInteger EXIT_CODE_ADDR = BigInteger.valueOf(SZM_INT_MAX_VALUE + 1);

    /**
     * 当前栈帧所在位置的地址，栈帧处的变量表示下一个栈帧的偏移量
     */
    static final BigInteger CURRENT_FRAME_POSITION_ADDR = BigInteger.valueOf(SZM_INT_MAX_VALUE + 100);

    /**
     * 局部变量栈的起始地址。
     */
    static final BigInteger LOCAL_VARIABLE_STACK_ADDR = BigInteger.valueOf(SZM_INT_MAX_VALUE + 101);

    @Override
    public ExecutableParameterCommand<Integer> loadLocal(int index){
        return new SimpleParameterCommand<>(SzmCommandType.V_LOAD, index,
            (BiConsumer<WhitespaceVM, Integer> & Serializable)(vm, param) -> {
                BigInteger framePosition = vm.getHeapMemory().retrieve(CURRENT_FRAME_POSITION_ADDR);
                BigInteger addr = framePosition.subtract(BigInteger.valueOf(param));
                vm.getOperandStack().push(vm.getHeapMemory().retrieve(addr));
            }
        );
    }

    @Override
    public ExecutableParameterCommand<Integer> storeLocal(int index){
        return new SimpleParameterCommand<>(SzmCommandType.V_STORE, index,
            (BiConsumer<WhitespaceVM, Integer> & Serializable)(vm, param) -> {
                BigInteger framePosition = vm.getHeapMemory().retrieve(CURRENT_FRAME_POSITION_ADDR);
                BigInteger addr = framePosition.subtract(BigInteger.valueOf(param));
                vm.getHeapMemory().store(addr, vm.getOperandStack().pop());
            }
        );
    }

    @Override
    public ExecutableParameterCommand<Integer> loadGlobal(int index){
        return new SimpleParameterCommand<>(SzmCommandType.V_GLOAD, index,
            (BiConsumer<WhitespaceVM, Integer> & Serializable)(vm, param) -> {
                BigInteger value = vm.getHeapMemory().retrieve(BigInteger.valueOf(param));
                vm.getOperandStack().push(value);
            }
        );
    }

    @Override
    public ExecutableParameterCommand<Integer> storeGlobal(int index){
        return new SimpleParameterCommand<>(SzmCommandType.V_GSTORE, index,
            (BiConsumer<WhitespaceVM, Integer> & Serializable)(vm, param) -> {
                BigInteger value = vm.getOperandStack().pop();
                vm.getHeapMemory().store(BigInteger.valueOf(param), value);
            }
        );
    }

    @Override
    public ExecutableCommand cmp(){
        return new SimpleCommand(SzmCommandType.A_CMP,
            (Consumer<WhitespaceVM> & Serializable)(vm) ->
            doMath(vm, (a, b) -> BigInteger.valueOf(a.compareTo(b))));
    }

    @Override
    public ExecutableParameterCommand<Integer> shl(int index){
        return new SimpleParameterCommand<>(SzmCommandType.A_SHL, index,
            (BiConsumer<WhitespaceVM, Integer> & Serializable)(vm, param) -> {
                vm.getOperandStack().push(vm.getOperandStack().pop().shiftLeft(param));
            });
    }

    @Override
    public ExecutableParameterCommand<Integer> shr(int index){
        return new SimpleParameterCommand<>(SzmCommandType.A_SHR, index,
            (BiConsumer<WhitespaceVM, Integer> & Serializable)(vm, param) -> {
                vm.getOperandStack().push(vm.getOperandStack().pop().shiftRight(param));
            });
    }

    @Override
    public ExecutableParameterCommand<Integer> ushr(int index){
        return new SimpleParameterCommand<>(SzmCommandType.A_USHR, index,
            (BiConsumer<WhitespaceVM, Integer> & Serializable)(vm, param) -> {
                vm.getOperandStack().push(BigInteger.valueOf(vm.getOperandStack().pop().intValue() >>> param));
            });
    }

    @Override
    public ExecutableCommand not(){
        return new SimpleCommand(SzmCommandType.A_NOT,
            (Consumer<WhitespaceVM> & Serializable)(vm) -> {
                vm.getOperandStack().push(vm.getOperandStack().pop().not());
            });
    }

    @Override
    public ExecutableCommand and(){
        return new SimpleCommand(SzmCommandType.A_AND,
            (Consumer<WhitespaceVM> & Serializable)(vm) ->
            doMath(vm, BigInteger::and));
    }

    @Override
    public ExecutableCommand or(){
        return new SimpleCommand(SzmCommandType.A_OR,
            (Consumer<WhitespaceVM> & Serializable)(vm) ->
            doMath(vm, BigInteger::or));
    }

    @Override
    public ExecutableCommand xor(){
        return new SimpleCommand(SzmCommandType.A_XOR,
            (Consumer<WhitespaceVM> & Serializable)(vm) ->
            doMath(vm, BigInteger::xor));
    }

    private static class FunctionCommand extends SimpleParameterCommand<String> implements LabelCommand{

        public FunctionCommand(Enum<? extends Type> type, String param, BiConsumer<WhitespaceVM, String> action){
            super(type, param, action);
        }
    }

    @Override
    public LabelCommand function(String label, int frameSize){
        return new FunctionCommand(SzmCommandType.F_FUNC, label,
            (BiConsumer<WhitespaceVM, String> & Serializable)(vm, param) -> {
                if (!vm.getHeapMemory().isAvailable(CURRENT_FRAME_POSITION_ADDR)){
                    vm.getHeapMemory().store(CURRENT_FRAME_POSITION_ADDR, LOCAL_VARIABLE_STACK_ADDR);
                    vm.getHeapMemory().store(LOCAL_VARIABLE_STACK_ADDR, BigInteger.ZERO);
                }
                BigInteger framePosition = vm.getHeapMemory().retrieve(CURRENT_FRAME_POSITION_ADDR);
                // 初始化栈帧空间
                for (int i = 1; i <= frameSize; i++){
                    vm.getHeapMemory().store(framePosition.add(BigInteger.valueOf(i)), BigInteger.ZERO);
                }
                // 放置下一个栈帧记录
                framePosition = framePosition.add(BigInteger.valueOf(frameSize + 1));
                vm.getHeapMemory().store(framePosition, BigInteger.valueOf(frameSize));
                vm.getHeapMemory().store(CURRENT_FRAME_POSITION_ADDR, framePosition);
            });
    }

    @Override
    public ExecutableParameterCommand<String> jumpIfPositive(String label){
        return new SimpleParameterCommand<>(SzmCommandType.F_JUMPP, label,
            (BiConsumer<WhitespaceVM, String> & Serializable)(vm, param) -> {
                if (vm.getOperandStack().pop().compareTo(BigInteger.ZERO) > 0){
                    vm.getProcessor().jump(param);
                }
            });
    }

    @Override
    public ExecutableCommand ret(){
        return new SimpleCommand(CommandType.F_RETURN,
            (Consumer<WhitespaceVM> & Serializable)(vm) -> {
                BigInteger framePosition = vm.getHeapMemory().retrieve(CURRENT_FRAME_POSITION_ADDR);
                int frameSize = vm.getHeapMemory().retrieve(framePosition).intValue();
                // 清理栈帧
                for (int i = 0; i <= frameSize; i++){
                    vm.getHeapMemory().free(framePosition.subtract(BigInteger.valueOf(i)));
                }
                // 移动栈帧
                framePosition = framePosition.subtract(BigInteger.valueOf(frameSize + 1));
                vm.getHeapMemory().store(CURRENT_FRAME_POSITION_ADDR, framePosition);

                vm.getProcessor().ret();
            });
    }

    @Override
    public ExecutableCommand exit(){
        return new SimpleCommand(CommandType.F_EXIT,
            (Consumer<WhitespaceVM> & Serializable)(vm) -> {
                vm.getProcessor().end();
                vm.getHeapMemory().store(EXIT_CODE_ADDR, BigInteger.ZERO);
            });
    }

    @Override
    public ExecutableParameterCommand<Integer> exit(int statusCode){
        return new SimpleParameterCommand<>(SzmCommandType.F_EXIT2, statusCode,
            (BiConsumer<WhitespaceVM, Integer> & Serializable)(vm, param) -> {
                vm.getProcessor().end();
                vm.getHeapMemory().store(EXIT_CODE_ADDR, BigInteger.valueOf(param));
            });
    }

    private static class DummyCommand extends AbstractCommand{

        public DummyCommand(Enum<? extends Type> type){
            super(type);
        }
    }

    @Override
    public Command breakpoint(){
        return new DummyCommand(SzmCommandType.R_BREAKPOINT);
    }

    @Override
    public Command impdep1(){
        return new DummyCommand(SzmCommandType.R_IMPDEP1);
    }
}
