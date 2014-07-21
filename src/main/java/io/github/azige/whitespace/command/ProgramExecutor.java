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
 * 用于执行Whitespace程序的执行器。执行器有一个从0开始的指令指针，反复执行程序中的指令并移动指针，直到程序末尾。
 *
 * @author Azige
 */
public interface ProgramExecutor{

    /**
     * 执行单条语句，并将指令指针向后移动
     *
     * @return 如果指令指针移动，则为true，如果已经到达程序末尾则为false
     */
    boolean executeOne();

    /**
     * 获得当前指令指针所标识的指令
     *
     * @return 当前指令
     */
    Command getCurrentCommand();

    /**
     * 持续执行指令直到程序末尾
     *
     * @param fromStart 若为true，则在开始执行前先重置指令指针
     */
    void executeAll(boolean fromStart);

    /**
     * 获得当前指令指针的位置
     *
     * @return 指令指针的位置
     */
    int getLocation();

    /**
     * 获得此执行器正在执行的程序
     *
     * @return
     */
    Program getProgram();

    /**
     * 跳转到指定的标签
     *
     * @param label
     */
    void jump(String label);

    /**
     * 重置指令指针到程序开始处
     */
    void reset();

}
