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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.github.azige.whitespace.WhitespaceException;

/**
 * 表示一个Whitespace程序。一个程序即是特定的指令序列的组合。程序也是可以序列化的
 *
 * @author Azige
 */
public class Program implements Serializable{

    private final List<Command> commands;
    private final Map<String, Integer> labelMap;

    private Program(List<Command> commands, Map<String, Integer> labelMap){
        this.commands = Collections.unmodifiableList(new ArrayList<>(commands));
        this.labelMap = Collections.unmodifiableMap(new LinkedHashMap<>(labelMap));
    }

    public List<Command> getCommands(){
        return commands;
    }

    public Map<String, Integer> getLabelMap(){
        return labelMap;
    }

    public static class Builder{

        private final List<Command> commands = new ArrayList<>();
        private final Map<String, Integer> labelMap = new LinkedHashMap<>();

        public Builder addCommand(Command command){
            if (command instanceof LabelCommand){
                return addCommand((LabelCommand)command);
            }
            commands.add(command);
            return this;
        }

        public Builder addCommand(LabelCommand command){
            String label = command.getLabel();
            if (labelMap.containsKey(label)){
                throw new WhitespaceException("标签" + label + "已存在。");
            }
            labelMap.put(label, commands.size());
            commands.add(command);
            return this;
        }

        public Program build(){
            return new Program(commands, labelMap);
        }
    }
}
