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

/**
 * 标签指令的简单实现。此类对象的指令类型一定是{@link CommandType#F_MARK}。
 *
 * @author Azige
 */
public class SimpleLabelCommand extends AbstractCommand implements LabelCommand{

    private final String label;

    /**
     * 以一个标签构造对象。
     *
     * @param label 标签
     */
    public SimpleLabelCommand(String label){
        super(CommandType.F_MARK);
        this.label = label;
    }

    @Override
    public String getParam(){
        return label;
    }
}
