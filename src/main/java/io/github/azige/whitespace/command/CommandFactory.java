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

import java.math.BigInteger;

/**
 * 用于构造指令的工厂类。
 *
 * @author Azige
 */
public interface CommandFactory{

    ExecutableParameterCommand<BigInteger> push(BigInteger number);

    ExecutableCommand discard();

    ExecutableCommand dup();

    ExecutableParameterCommand<Integer> dup(int index);

    ExecutableParameterCommand<Integer> slide(int index);

    ExecutableCommand swap();

    ExecutableCommand add();

    ExecutableCommand sub();

    ExecutableCommand mul();

    ExecutableCommand div();

    ExecutableCommand mod();

    ExecutableCommand store();

    ExecutableCommand retrieve();

    LabelCommand mark(String label);

    ExecutableParameterCommand<String> call(String label);

    ExecutableParameterCommand<String> jump(String label);

    ExecutableParameterCommand<String> jumpIfZero(String label);

    ExecutableParameterCommand<String> jumpIfNegative(String label);

    ExecutableCommand ret();

    ExecutableCommand exit();

    ExecutableCommand printChar();

    ExecutableCommand printNumber();

    ExecutableCommand readChar();

    ExecutableCommand readNumber();
}
