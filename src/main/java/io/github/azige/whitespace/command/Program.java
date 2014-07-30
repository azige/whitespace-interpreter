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
 * 表示一个Whitespace程序。一个程序即是特定的指令序列的组合。程序也是可以序列化的。
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

    /**
     * 获得包含指令序列的列表，此列表是不可变的。
     *
     * @return 包含指令序列的列表
     */
    public List<Command> getCommands(){
        return commands;
    }

    /**
     * 获得标签与指令索引的映射，此映射是不可变的。
     *
     * @return 标签与指令索引的映射
     */
    public Map<String, Integer> getLabelMap(){
        return labelMap;
    }

    /**
     * Program的构建器，用于将指令组装成程序。
     */
    public static class Builder{

        private final List<Command> commands = new ArrayList<>();
        private final Map<String, Integer> labelMap = new LinkedHashMap<>();

        /**
         * 添加一条指令。
         *
         * @param command 要添加的指令
         * @return 此对象本身
         */
        public Builder addCommand(Command command){
            if (command instanceof LabelCommand){
                return addCommand((LabelCommand)command);
            }
            commands.add(command);
            return this;
        }

        /**
         * 添加一条标签指令。
         *
         * @param command 要添加的指令
         * @return 此对象本身
         */
        public Builder addCommand(LabelCommand command){
            String label = command.getLabel();
            if (labelMap.containsKey(label)){
                throw new WhitespaceException("标签" + label + "已存在。");
            }
            labelMap.put(label, commands.size());
            commands.add(command);
            return this;
        }

        /**
         * 用现有的指令构造一个程序对象。
         *
         * @return 对应的程序对象
         */
        public Program build(){
            return new Program(commands, labelMap);
        }
    }
}
