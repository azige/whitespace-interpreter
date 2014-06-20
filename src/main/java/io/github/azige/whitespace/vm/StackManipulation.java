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

import java.math.BigInteger;

/**
 * 定义了Whitespace中的栈操作指令的接口。<br>
 *
 * @author Azige
 */
public interface StackManipulation{

    /**
     * 向操作数栈中压入一个数。
     *
     * @param number 入栈的数
     */
    void push(BigInteger number);

    /**
     * 丢弃栈顶元素。
     */
    void discard();

    /**
     * 将栈顶元素复制一份。
     */
    void dup();

    /**
     * 将栈顶开始的第{@code index}个元素复制一份到栈顶。
     *
     * @param index 要复制的元素的索引
     */
    void dup(int index);

    /**
     * 从栈中移除第{@code index}个元素。
     *
     * @param index 要移除的元素的索引
     */
    void remove(int index);

    /**
     * 交换栈顶的两个元素。
     */
    void swap();

}
