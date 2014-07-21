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

import java.util.function.BiConsumer;

import io.github.azige.whitespace.vm.WhitespaceVM;

/**
 * 有参数的指令的简单实现。
 *
 * @author Azige
 * @param <T> 此指令的参数类型
 */
public class SimpleParameterCommand<T> extends Command.AbstractCommand implements ParameterCommand<T>{

    private final T param;
    private final BiConsumer<WhitespaceVM, T> c;

    public SimpleParameterCommand(CommandType type, T param, BiConsumer<WhitespaceVM, T> c){
        super(type);
        this.param = param;
        this.c = c;
    }

    @Override
    public T getParam(){
        return param;
    }

    @Override
    public void execute(WhitespaceVM vm){
        c.accept(vm, param);
    }
}
