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

import static io.github.azige.whitespace.command.CommandType.*;

import java.math.BigInteger;

import io.github.azige.whitespace.vm.WhitespaceVM;

/**
 *
 * @author Azige
 */
public abstract class CommandFactory{

    public abstract Command<BigInteger> push(BigInteger number);

    public abstract Command<?> discard();

    public abstract Command<?> dup();

    public abstract Command<Integer> dup(int index);

    public abstract Command<Integer> remove(int index);

    public abstract Command<?> swap();

    public abstract Command<?> add();

    public abstract Command<?> sub();

    public abstract Command<?> mul();

    public abstract Command<?> div();

    public abstract Command<?> mod();

    public abstract Command<?> store();

    public abstract Command<?> retrieve();

    public abstract Command<String> mark(String label);

    public abstract Command<String> callSubroutine(String label);

    public abstract Command<String> jump(String label);

    public abstract Command<String> jumpIfZero(String label);

    public abstract Command<String> jumpIfNegative(String label);

    public abstract Command<?> returnFromSubroutine();

    public abstract Command<?> exit();

    public abstract Command<?> printChar();

    public abstract Command<?> printNumber();

    public abstract Command<?> readChar();

    public abstract Command<?> readNumber();
}
