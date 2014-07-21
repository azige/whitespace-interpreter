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
package io.github.azige.whitespace.vm;

import io.github.azige.whitespace.command.Program;
import io.github.azige.whitespace.command.ProgramExecutor;

/**
 * Whitespace专用的处理器，可以装载程序并执行。此接口定义的对象可以用于运行多个程序，不需要在初始化状态就已经装载了程序，
 * 在未装载程序的情况下调用{@code ProgramExecutor}的方法将引起异常。
 *
 * @author Azige
 */
public interface Processor extends ProgramExecutor{

    /**
     * 装载新的程序，并取代旧的，前一个程序相关的资源都会被舍弃。
     *
     * @param program 要装载的程序
     */
    void loadProgram(Program program);

    /**
     * 检查是否已经装载了程序并可以执行。此方法返回false的情况下，对{@code ProgramExecutor}的方法进行调用将引起异常。
     *
     * @return 如果已准备好，则为true
     */
    boolean isReady();
}
