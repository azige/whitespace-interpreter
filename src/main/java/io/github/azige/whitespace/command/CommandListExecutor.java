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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.azige.whitespace.WhitespaceException;
import io.github.azige.whitespace.vm.WhitespaceVM;

/**
 *
 * @author Azige
 */
public class CommandListExecutor{

    private final WhitespaceVM vm;
    private final List<Command<?>> commands= new ArrayList<>();
    private final Map<String, Integer> labelMap = new HashMap<>();
    private int location;

    public CommandListExecutor(WhitespaceVM vm){
        this.vm = vm;
    }

    public int getLocation(){
        return location;
    }

    public void setLocation(int location){
        this.location = location;
    }

    public void addCommand(Command<?> command){
        if (command.getType() == CommandType.F_MARK){
            String label = (String)command.getParameter();
            if (labelMap.containsKey(label)){
                throw new WhitespaceException("标签" + label + "已存在。");
            }
            labelMap.put(label, location);
        }else{
            commands.add(command);
        }
    }

    public boolean next(){
        if (location < commands.size()){
            commands.get(location++).execute(vm);
            return true;
        }else{
            return false;
        }
    }

    public void run(WhitespaceVM vm){
        while (next()){
        }
    }
}
