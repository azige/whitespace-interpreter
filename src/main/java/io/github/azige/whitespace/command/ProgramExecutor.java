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

import io.github.azige.whitespace.WhitespaceException;

/**
 * Whitespace程序执行器。
 *
 * @author Azige
 */
public class ProgramExecutor{

    private final Program program;
    private int location = 0;

    public ProgramExecutor(Program program){
        this.program = program;
    }

    public Program getProgram(){
        return program;
    }

    public int getLocation(){
        return location;
    }

    public Command getCurrentCommand(){
        return program.getCommands().get(location);
    }

    public void jump(String label){
        if (!program.getLabelMap().containsKey(label)){
            throw new WhitespaceException("标签" + label + "不存在。");
        }
        location = program.getLabelMap().get(label);
    }

    public void reset(){
        location = 0;
    }

    public boolean next(){
        if (location < program.getCommands().size()){
            Command command = program.getCommands().get(location++);
            if (command instanceof ExecutableCommand){
                ((ExecutableCommand)command).execute();
            }
            return true;
        }else{
            return false;
        }
    }

    public void execute(){
        while (next()){
            // Do nothing
        }
    }
}
