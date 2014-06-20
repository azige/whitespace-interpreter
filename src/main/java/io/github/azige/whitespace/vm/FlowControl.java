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

/**
 * 定义了Whitespace中的流程控制指令的接口。<br>
 * 流程控制指令的IMP为[LF]，指令的参数的标签为[Space]与[Tab]的序列。<br>
 * 此接口也定义了程序执行的方法，通过用{@link #addCommand}方法将指令加入指令队列，
 * 再反复调用{@link #nextCommand}方法来执行命令。实现类应当拥有一个指令指针，标记当前执行到的指令所在的位置。
 *
 * @author Azige
 */
public interface FlowControl{

    /**
     * 呼叫指定的标签的子程序。
     *
     * @param label 要呼叫的标签
     */
    void callSubroutine(String label);

    /**
     * 无条件转移到指定的标签。
     *
     * @param label 要跳转的标签
     */
    void jump(String label);

    /**
     * 如果栈顶元素为0，则跳转到指定的标签。
     *
     * @param label 要跳转的标签
     */
    void jumpIfZero(String label);

    /**
     * 如果栈顶元素为负，则跳转到指定的标签。
     *
     * @param label 要跳转的标签
     */
    void jumpIfNegative(String label);

    /**
     * 从子程序中返回。
     */
    void returnFromSubroutine();

    /**
     * 结束程序，通常应当是将指令指针移到指令队列最后。
     */
    void exit();
}
