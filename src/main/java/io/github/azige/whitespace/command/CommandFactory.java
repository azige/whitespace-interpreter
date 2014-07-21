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
public abstract class CommandFactory{

    public abstract ParameterCommand<BigInteger> push(BigInteger number);

    public abstract ExecutableCommand discard();

    public abstract ExecutableCommand dup();

    public abstract ParameterCommand<Integer> dup(int index);

    public abstract ParameterCommand<Integer> remove(int index);

    public abstract ExecutableCommand swap();

    public abstract ExecutableCommand add();

    public abstract ExecutableCommand sub();

    public abstract ExecutableCommand mul();

    public abstract ExecutableCommand div();

    public abstract ExecutableCommand mod();

    public abstract ExecutableCommand store();

    public abstract ExecutableCommand retrieve();

    public abstract LabelCommand mark(String label);

    public abstract ParameterCommand<String> callSubroutine(String label);

    public abstract ParameterCommand<String> jump(String label);

    public abstract ParameterCommand<String> jumpIfZero(String label);

    public abstract ParameterCommand<String> jumpIfNegative(String label);

    public abstract ExecutableCommand returnFromSubroutine();

    public abstract ExecutableCommand exit();

    public abstract ExecutableCommand printChar();

    public abstract ExecutableCommand printNumber();

    public abstract ExecutableCommand readChar();

    public abstract ExecutableCommand readNumber();
}
