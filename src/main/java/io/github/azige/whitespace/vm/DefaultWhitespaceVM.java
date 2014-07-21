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
package io.github.azige.whitespace.vm;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.github.azige.whitespace.WhitespaceException;
import io.github.azige.whitespace.command.Command;
import io.github.azige.whitespace.command.Program;
import io.github.azige.whitespace.command.ProgramExecutor;
import io.github.azige.whitespace.command.SimpleProgramExecutor;

/**
 * WhitespaceVM的默认实现。
 *
 * @author Azige
 */
public class DefaultWhitespaceVM implements WhitespaceVM{

    private final OperandStack operandStack = new OperandStack(){

        private final LinkedList<BigInteger> list = new LinkedList<>();

        @Override
        public void push(BigInteger number){
            list.push(number);
        }

        @Override
        public BigInteger pop(){
            checkEmpty();
            return list.pop();
        }

        @Override
        public BigInteger peek(){
            checkEmpty();
            return list.peek();
        }

        @Override
        public BigInteger peek(int index){
            checkBound(index);
            return list.get(index);
        }

        @Override
        public void dup(){
            checkEmpty();
            push(peek());
        }

        @Override
        public void dup(int index){
            checkBound(index);
            push(peek(index));
        }

        @Override
        public BigInteger remove(int index){
            checkBound(index);
            return list.remove(index);
        }

        @Override
        public void swap(){
            checkBound(1);
            push(remove(1));
        }

        @Override
        public List<BigInteger> toList(){
            return Collections.unmodifiableList(list);
        }

        private void checkEmpty(){
            if (list.isEmpty()){
                throw new WhitespaceException("栈为空");
            }
        }

        private void checkBound(int index){
            if (index < 0 || index >= list.size()){
                throw new WhitespaceException("索引越界：" + index);
            }
        }
    };

    private final HeapMemory heapMemory = new HeapMemory() {

        private final Map<BigInteger, BigInteger> map = new HashMap<>();

        @Override
        public void store(BigInteger address, BigInteger data){
            map.put(address, data);
        }

        @Override
        public BigInteger retrieve(BigInteger address){
            checkAddress(address);
            return map.get(address);
        }

        @Override
        public BigInteger free(BigInteger address){
            checkAddress(address);
            return map.remove(address);
        }

        @Override
        public Map<BigInteger, BigInteger> toMap(){
            return Collections.unmodifiableMap(map);
        }

        private void checkAddress(BigInteger address){
            if (!map.containsKey(address)){
                throw new WhitespaceException("地址不可用：" + address);
            }
        }
    };

    private final IODevice iODevice = new IODevice() {

        private Reader input = new InputStreamReader(System.in);
        private Writer output = new OutputStreamWriter(System.out);

        @Override
        public Reader getInput(){
            return input;
        }

        @Override
        public void setInput(Reader input){
            this.input = input;
        }

        @Override
        public Writer getOutput(){
            return output;
        }

        @Override
        public void setOutput(Writer output){
            this.output = output;
        }
    };

    private final Processor processor = new Processor() {

        private ProgramExecutor executor = null;

        @Override
        public void loadProgram(Program program){
            executor = new SimpleProgramExecutor(DefaultWhitespaceVM.this, program);
        }

        @Override
        public boolean isReady(){
            return executor != null;
        }

        @Override
        public boolean executeOne(){
            checkReady();
            return executor.executeOne();
        }

        @Override
        public Command getCurrentCommand(){
            checkReady();
            return executor.getCurrentCommand();
        }

        @Override
        public void executeAll(boolean fromStart){
            checkReady();
            executor.executeAll(fromStart);
        }

        @Override
        public int getLocation(){
            checkReady();
            return executor.getLocation();
        }

        @Override
        public Program getProgram(){
            checkReady();
            return executor.getProgram();
        }

        @Override
        public void jump(String label){
            checkReady();
            executor.jump(label);
        }

        @Override
        public void reset(){
            checkReady();
            executor.reset();
        }

        private void checkReady(){
            if (!isReady()){
                throw new WhitespaceException("未装载程序");
            }
        }
    };

    public DefaultWhitespaceVM(){
    }

    @Override
    public OperandStack getOperandStack(){
        return operandStack;
    }

    @Override
    public HeapMemory getHeapMemory(){
        return heapMemory;
    }

    @Override
    public IODevice getIODevice(){
        return iODevice;
    }

    @Override
    public Processor getProcessor(){
        return processor;
    }
}
