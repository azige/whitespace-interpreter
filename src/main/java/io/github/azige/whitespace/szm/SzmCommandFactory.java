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
package io.github.azige.whitespace.szm;

import io.github.azige.whitespace.command.Command;
import io.github.azige.whitespace.command.CommandFactory;
import io.github.azige.whitespace.command.ExecutableCommand;
import io.github.azige.whitespace.command.ExecutableParameterCommand;
import io.github.azige.whitespace.command.LabelCommand;

/**
 * 水沝淼语言的指令工厂，追加了Whitespace中没有定义的一些指令。
 *
 * @author Azige
 */
public interface SzmCommandFactory extends CommandFactory{

    ExecutableParameterCommand<Integer> loadLocal(int index);

    ExecutableParameterCommand<Integer> storeLocal(int index);

    ExecutableParameterCommand<Integer> loadGlobal(int index);

    ExecutableParameterCommand<Integer> storeGlobal(int index);

    ExecutableCommand cmp();

    ExecutableParameterCommand<Integer> shl(int index);

    ExecutableParameterCommand<Integer> shr(int index);

    ExecutableParameterCommand<Integer> ushr(int index);

    ExecutableCommand not();

    ExecutableCommand and();

    ExecutableCommand or();

    ExecutableCommand xor();

    LabelCommand function(String label, int frameSize);

    ExecutableParameterCommand<String> jumpIfPositive(String label);

    ExecutableParameterCommand<Integer> exit(int statusCode);

    Command breakpoint();

    Command impdep1();
}
