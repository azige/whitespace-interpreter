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

import java.util.function.Consumer;

import io.github.azige.whitespace.vm.WhitespaceVM;

/**
 * 没有参数的简单指令的实现。
 *
 * @author Azige
 */
public class SimpleCommand extends AbstractCommand implements ExecutableCommand{

    private final Consumer<WhitespaceVM> action;

    /**
     * 以指令类型和动作来构造一个指令。
     *
     * @param type 指令类型
     * @param action 可序列化的动作
     */
    public SimpleCommand(CommandType type, Consumer<WhitespaceVM> action){
        super(type);
        this.action = action;
    }

    @Override
    public void execute(WhitespaceVM vm){
        action.accept(vm);
    }
}
