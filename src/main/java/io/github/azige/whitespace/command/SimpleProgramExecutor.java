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

import java.util.LinkedList;

import io.github.azige.whitespace.WhitespaceException;
import io.github.azige.whitespace.vm.WhitespaceVM;

/**
 * Whitespace程序执行器。此对象在生命周期内只能用于执行一个程序，可以执行多次。
 *
 * @author Azige
 */
public class SimpleProgramExecutor implements ProgramExecutor{

    private final WhitespaceVM vm;
    private final Program program;
    private final LinkedList<Integer> callStack = new LinkedList<>();
    private int location = 0;

    public SimpleProgramExecutor(WhitespaceVM vm, Program program){
        this.vm = vm;
        this.program = program;
    }

    @Override
    public Program getProgram(){
        return program;
    }

    @Override
    public int getLocation(){
        return location;
    }

    @Override
    public Command getCurrentCommand(){
        if (location >= program.getCommands().size()){
            return null;
        }
        return program.getCommands().get(location);
    }

    @Override
    public void jump(String label){
        if (!program.getLabelMap().containsKey(label)){
            throw new WhitespaceException("标签" + label + "不存在。");
        }
        location = program.getLabelMap().get(label);
    }

    @Override
    public void reset(){
        location = 0;
        callStack.clear();
    }

    @Override
    public void end(){
        location = program.getCommands().size();
    }

    @Override
    public boolean executeOne(){
        if (location < program.getCommands().size()){
            Command command = program.getCommands().get(location++);
            if (command instanceof ExecutableCommand){
                ((ExecutableCommand)command).execute(vm);
            }
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void executeAll(boolean fromStart){
        if (fromStart){
            reset();
        }

        while (executeOne()){
            // Do nothing
        }
    }

    @Override
    public void call(String label){
        callStack.push(location);
        jump(label);
    }

    @Override
    public void ret(){
        location = callStack.pop();
    }

}
