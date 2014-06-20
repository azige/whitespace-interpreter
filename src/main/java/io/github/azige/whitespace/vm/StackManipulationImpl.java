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

import java.math.BigInteger;
import java.util.NoSuchElementException;

/**
 * StackManipulation的实现类。
 * @author Azige
 */
public class StackManipulationImpl implements StackManipulation{

    private final WhitespaceVM vm;

    StackManipulationImpl(WhitespaceVMImpl vm){
        this.vm = vm;
    }

    /**
     * 向操作数栈中压入一个数。
     * @param number 入栈的数
     */
    @Override
    public void push(BigInteger number){
        if (number == null){
            throw new NullPointerException();
        }
        vm.getOpStack().push(number);
    }

    @Override
    public void dup(){
        if (vm.getOpStack().isEmpty()){
            //throw new WhitespaceException("操作数栈为空");
            throw new NoSuchElementException();
        }
        vm.getOpStack().push(vm.getOpStack().peek());
    }

    @Override
    public void dup(int index){
        vm.getOpStack().push(vm.getOpStack().get(index));
    }

    @Override
    public void remove(int index){
        vm.getOpStack().remove(index);
    }

    @Override
    public void swap(){
        vm.getOpStack().push(vm.getOpStack().remove(1));
    }

    @Override
    public void discard(){
        vm.getOpStack().pop();
    }
}
