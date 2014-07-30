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
 * 抽象指令，包含指令的基础部分的实现。
 *
 * @author Azige
 */
public abstract class AbstractCommand implements Command{

    private final CommandType type;

    /**
     * 用指定的指令类型构造对象。
     *
     * @param type 此对象的指令类型
     */
    public AbstractCommand(CommandType type){
        if (type == null){
            throw new NullPointerException();
        }
        this.type = type;
    }

    @Override
    public CommandType getType(){
        return type;
    }

}
