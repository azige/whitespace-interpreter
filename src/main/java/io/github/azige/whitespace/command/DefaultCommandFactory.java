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
package io.github.azige.whitespace.command;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;

import io.github.azige.whitespace.WhitespaceException;
import io.github.azige.whitespace.vm.WhitespaceVM;

/**
 * 指令工厂的默认实现。
 *
 * @author Azige
 */
public class DefaultCommandFactory implements CommandFactory{

    @Override
    public ParameterCommand<BigInteger> push(BigInteger number){
        return new SimpleParameterCommand<>(CommandType.S_PUSH, number,
            (BiConsumer<WhitespaceVM, BigInteger> & Serializable)((vm, param) ->
            vm.getOperandStack().push(param)));
    }

    @Override
    public ExecutableCommand discard(){
        return new SimpleCommand(CommandType.S_DISCARD,
            (Consumer<WhitespaceVM> & Serializable)(vm ->
            vm.getOperandStack().pop()));
    }

    @Override
    public ExecutableCommand dup(){
        return new SimpleCommand(CommandType.S_DUP,
            (Consumer<WhitespaceVM> & Serializable)(vm ->
            vm.getOperandStack().dup()));
    }

    @Override
    public ParameterCommand<Integer> dup(int index){
        return new SimpleParameterCommand<>(CommandType.S_DUP2, index,
            (BiConsumer<WhitespaceVM, Integer> & Serializable)((vm, param) ->
            vm.getOperandStack().dup(param)));
    }

    @Override
    public ParameterCommand<Integer> remove(int index){
        return new SimpleParameterCommand<>(CommandType.S_REMOVE, index,
            (BiConsumer<WhitespaceVM, Integer> & Serializable)((vm, param) ->
            vm.getOperandStack().remove(param)));
    }

    @Override
    public ExecutableCommand swap(){
        return new SimpleCommand(CommandType.S_SWAP,
            (Consumer<WhitespaceVM> & Serializable)(vm -> vm.getOperandStack().swap()));
    }

    private static void doMath(WhitespaceVM vm, BinaryOperator<BigInteger> func){
        BigInteger right = vm.getOperandStack().pop();
        BigInteger left = vm.getOperandStack().pop();
        vm.getOperandStack().push(func.apply(left, right));
    }

    @Override
    public ExecutableCommand add(){
        return new SimpleCommand(CommandType.A_ADD,
            (Consumer<WhitespaceVM> & Serializable)(vm ->
            doMath(vm, BigInteger::add)));
    }

    @Override
    public ExecutableCommand sub(){
        return new SimpleCommand(CommandType.A_SUB,
            (Consumer<WhitespaceVM> & Serializable)(vm ->
            doMath(vm, BigInteger::subtract)));
    }

    @Override
    public ExecutableCommand mul(){
        return new SimpleCommand(CommandType.A_MUL,
            (Consumer<WhitespaceVM> & Serializable)(vm ->
            doMath(vm, BigInteger::multiply)));
    }

    @Override
    public ExecutableCommand div(){
        return new SimpleCommand(CommandType.A_DIV,
            (Consumer<WhitespaceVM> & Serializable)(vm ->
            doMath(vm, BigInteger::multiply)));
    }

    @Override
    public ExecutableCommand mod(){
        return new SimpleCommand(CommandType.A_MOD,
            (Consumer<WhitespaceVM> & Serializable)(vm ->
            doMath(vm, BigInteger::mod)));
    }

    @Override
    public ExecutableCommand store(){
        return new SimpleCommand(CommandType.H_STORE,
            (Consumer<WhitespaceVM> & Serializable)(vm -> {
                BigInteger data = vm.getOperandStack().pop();
                BigInteger address = vm.getOperandStack().pop();
                vm.getHeapMemory().store(address, data);
            }));
    }

    @Override
    public ExecutableCommand retrieve(){
        return new SimpleCommand(CommandType.H_RETRIEVE,
            (Consumer<WhitespaceVM> & Serializable)(vm -> {
                BigInteger address = vm.getOperandStack().pop();
                BigInteger data = vm.getHeapMemory().retrieve(address);
                vm.getOperandStack().push(data);
            }));
    }

    @Override
    public LabelCommand mark(String label){
        return new SimpleLabelCommand(label);
    }

    @Override
    public ParameterCommand<String> callSubroutine(String label){
        return new SimpleParameterCommand<>(CommandType.F_CALL, label,
            (BiConsumer<WhitespaceVM, String> & Serializable)((vm, param) ->
            vm.getProcessor().call(param)));
    }

    @Override
    public ParameterCommand<String> jump(String label){
        return new SimpleParameterCommand<>(CommandType.F_JUMP, label,
            (BiConsumer<WhitespaceVM, String> & Serializable)((vm, param) ->
            vm.getProcessor().jump(param)));
    }

    @Override
    public ParameterCommand<String> jumpIfZero(String label){
        return new SimpleParameterCommand<>(CommandType.F_JUMPZ, label,
            (BiConsumer<WhitespaceVM, String> & Serializable)((vm, param) -> {
                if (vm.getOperandStack().pop().equals(BigInteger.ZERO)){
                    vm.getProcessor().jump(param);
                }
            }));
    }

    @Override
    public ParameterCommand<String> jumpIfNegative(String label){
        return new SimpleParameterCommand<>(CommandType.F_JUMPN, label,
            (BiConsumer<WhitespaceVM, String> & Serializable)((vm, param) -> {
                if (vm.getOperandStack().pop().compareTo(BigInteger.ZERO) < 0){
                    vm.getProcessor().jump(param);
                }
            }));
    }

    @Override
    public ExecutableCommand returnFromSubroutine(){
        return new SimpleCommand(CommandType.F_RETURN,
            (Consumer<WhitespaceVM> & Serializable)(vm ->
            vm.getProcessor().ret()));
    }

    @Override
    public ExecutableCommand exit(){
        return new SimpleCommand(CommandType.F_EXIT,
            (Consumer<WhitespaceVM> & Serializable)(vm ->
            vm.getProcessor().end()));
    }

    @Override
    public ExecutableCommand printChar(){
        return new SimpleCommand(CommandType.I_PCHAR,
            (Consumer<WhitespaceVM> & Serializable)(vm -> {
                BigInteger c = vm.getOperandStack().pop();
                vm.getIODevice().getOutput().printf("%c", (char)c.intValue());
            }));
    }

    @Override
    public ExecutableCommand printNumber(){
        return new SimpleCommand(CommandType.I_PNUM,
            (Consumer<WhitespaceVM> & Serializable)(vm -> {
                BigInteger c = vm.getOperandStack().pop();
                vm.getIODevice().getOutput().printf("%d", c);
            }));
    }

    @Override
    public ExecutableCommand readChar(){
        return new SimpleCommand(CommandType.I_RCHAR,
            (Consumer<WhitespaceVM> & Serializable)(vm -> {
                try{
                    BigInteger c = BigInteger.valueOf(vm.getIODevice().getInput().read());
                    BigInteger address = vm.getOperandStack().pop();
                    vm.getHeapMemory().store(address, c);
                }catch (IOException ex){
                    throw new WhitespaceException(ex);
                }
            }));
    }

    @Override
    public ExecutableCommand readNumber(){
        return new SimpleCommand(CommandType.I_RNUM,
            (Consumer<WhitespaceVM> & Serializable)(vm -> {
                Scanner scanner = new Scanner(vm.getIODevice().getInput());
                try{
                    BigInteger number = scanner.nextBigInteger();
                    BigInteger address = vm.getOperandStack().pop();
                    vm.getHeapMemory().store(address, number);
                }catch (NoSuchElementException ex){
                    throw new WhitespaceException(ex);
                }
            }));
    }

}
