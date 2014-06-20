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
 *
 * @author Azige
 */
public abstract class AbstractCommand<T> implements Command<T>{

    private final CommandType type;
    private final T parameter;

    public AbstractCommand(CommandType type){
        this(type, null);
    }

    public AbstractCommand(CommandType type, T parameter){
        if (type == null){
            throw new NullPointerException();
        }
        this.type = type;
        this.parameter = parameter;
    }

    @Override
    public CommandType getType(){
        return type;
    }

    @Override
    public T getParameter(){
        return parameter;
    }

    @Override
    public String toString(){
        return "" + getType() + " " + (parameter == null ? "" : parameter);
    }
}
