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
 * 定义了Whitespace中的数学运算指令的接口。<br>
 * 数学运算指令的IMP为[Tab][Space]，所有指令都是对栈顶的两个元素进行操作，并将结果放回栈顶，先入栈的元素作为左操作数。
 *
 * @author Azige
 */
public interface Arithmetic{

    /**
     * 加法
     */
    void add();

    /**
     * 减法
     */
    void sub();

    /**
     * 乘法
     */
    void mul();

    /**
     * 除法
     */
    void div();

    /**
     * 取余
     */
    void mod();

}
