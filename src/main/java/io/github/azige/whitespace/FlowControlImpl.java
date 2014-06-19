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
package io.github.azige.whitespace;

import java.math.BigInteger;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * FlowControl的实现类。
 * @author Azige
 */
public class FlowControlImpl implements FlowControl{

    private final WhitespaceVM vm;
    private int location;
    final LinkedList<Command> commandList = new LinkedList<>();
    private final Deque<Integer> subroutineStack = new LinkedList<>();
    private final Map<String, Integer> labelMap = new HashMap<>();

    FlowControlImpl(WhitespaceVM vm){
        this.vm = vm;
    }

    @Override
    public void mark(String label){
        if (labelMap.containsKey(label)){
            throw new WhitespaceException("标签已存在");
        }
        labelMap.put(label, commandList.size());
    }

    @Override
    public void callSubroutine(String label){
        if (!labelMap.containsKey(label)){
            throw new WhitespaceException("标签不存在");
        }
        subroutineStack.push(location);
        location = labelMap.get(label);
    }

    @Override
    public void jump(String label){
        if (!labelMap.containsKey(label)){
            throw new WhitespaceException("标签不存在");
        }
        location = labelMap.get(label);
    }

    @Override
    public void jumpIfZero(String label){
        if (!labelMap.containsKey(label)){
            throw new WhitespaceException("标签不存在");
        }
        if (vm.getOpStack().pop().equals(BigInteger.ZERO)){
            location = labelMap.get(label);
        }
    }

    @Override
    public void jumpIfNegative(String label){
        if (!labelMap.containsKey(label)){
            throw new WhitespaceException("标签不存在");
        }
        if (vm.getOpStack().pop().compareTo(BigInteger.ZERO) < 0){
            location = labelMap.get(label);
        }
    }

    @Override
    public void returnFromSubroutine(){
        location = subroutineStack.pop();
    }

    @Override
    public void exit(){
        location = commandList.size();
    }

    @Override
    public void addCommand(Command command){
        commandList.add(command);
    }

    @Override
    public int getLocation(){
        return location;
    }

    @Override
    public boolean nextCommand(){
        if (location < commandList.size()){
            commandList.get(location++).run();
            return true;
        }else{
            return false;
        }
    }
}
