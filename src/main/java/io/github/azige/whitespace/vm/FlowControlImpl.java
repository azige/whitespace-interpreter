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
import java.util.Deque;
import java.util.LinkedList;

/**
 * FlowControl的实现类。
 *
 * @author Azige
 */
public class FlowControlImpl implements FlowControl{

    private final WhitespaceVM vm;
    private final Deque<Integer> subroutineStack = new LinkedList<>();

    FlowControlImpl(WhitespaceVM vm){
        this.vm = vm;
    }

    @Override
    public void callSubroutine(String label){
        subroutineStack.push(vm.getExecutor().getLocation());
        vm.getExecutor().jump(label);
    }

    @Override
    public void jump(String label){
        vm.getExecutor().jump(label);
    }

    @Override
    public void jumpIfZero(String label){
        if (vm.getOpStack().pop().equals(BigInteger.ZERO)){
            vm.getExecutor().jump(label);
        }
    }

    @Override
    public void jumpIfNegative(String label){
        if (vm.getOpStack().pop().compareTo(BigInteger.ZERO) < 0){
            vm.getExecutor().jump(label);
        }
    }

    @Override
    public void returnFromSubroutine(){
        vm.getExecutor().setLocation(subroutineStack.pop());
    }

    @Override
    public void exit(){
        vm.getExecutor().setLocation(vm.getExecutor().count());
    }
}
