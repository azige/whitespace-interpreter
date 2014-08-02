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

    ParameterCommand<BigInteger> push(BigInteger number);

    ExecutableCommand discard();

    ExecutableCommand dup();

    ParameterCommand<Integer> dup(int index);

    ParameterCommand<Integer> remove(int index);

    ExecutableCommand swap();

    ExecutableCommand add();

    ExecutableCommand sub();

    ExecutableCommand mul();

    ExecutableCommand div();

    ExecutableCommand mod();

    ExecutableCommand store();

    ExecutableCommand retrieve();

    LabelCommand mark(String label);

    ParameterCommand<String> callSubroutine(String label);

    ParameterCommand<String> jump(String label);

    ParameterCommand<String> jumpIfZero(String label);

    ParameterCommand<String> jumpIfNegative(String label);

    ExecutableCommand returnFromSubroutine();

    ExecutableCommand exit();

    ExecutableCommand printChar();

    ExecutableCommand printNumber();

    ExecutableCommand readChar();

    ExecutableCommand readNumber();
}
