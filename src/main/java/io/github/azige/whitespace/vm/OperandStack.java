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
import java.util.List;

/**
 * Whitespace虚拟机专用的操作数栈。除去常规的栈操作外，还有对应Whitespace栈操作指令的一些方法。
 *
 * @author Azige
 */
public interface OperandStack{

    /**
     * 向操作数栈中压入一个数。
     *
     * @param number 入栈的数
     */
    void push(BigInteger number);

    /**
     * 弹出栈顶元素。
     *
     * @return 栈顶元素
     */
    BigInteger pop();

    /**
     * 检查栈顶元素。
     *
     * @return 栈顶元素
     */
    BigInteger peek();

    /**
     * 检查栈顶开始的第{@code index}个元素。
     *
     * @param index 要检查的元素的索引
     * @return 索引位置的元素
     */
    BigInteger peek(int index);

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
     * @return 移除的元素
     */
    BigInteger remove(int index);

    /**
     * 移除栈顶的前{@code n}个元素，但是保留栈顶元素。
     *
     * @param n 要移除的元素个数
     */
    void slide(int n);

    /**
     * 修改从栈顶开始的第{@code index}的元素的值。
     *
     * @param index 要修改的元素的索引
     * @param number 修改的值
     */
    void modify(int index, BigInteger number);

    /**
     * 交换栈顶的两个元素。
     */
    void swap();

    /**
     * 获得此对象的<b>不可变</b>列表的表示形式。<br>
     * 此列表的0索引位置的元素为栈顶元素。
     *
     * @return 对应此对象的列表
     */
    List<BigInteger> toList();
}
