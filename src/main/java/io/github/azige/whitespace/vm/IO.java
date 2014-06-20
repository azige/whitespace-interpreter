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
 * 定义了Whitespace中的IO指令的接口。<br>
 * IO指令的IMP为[Tab][Tab]。
 *
 * @author Azige
 */
public interface IO{

    /**
     * 将操作数栈顶元素作为字符输出。<br>
     * 尽管Whitespace里没有规定使用何种编码，为了获得更好的兼容性，栈顶元素应该视作一个Unicode码位来转换为字符。
     */
    void printChar();

    /**
     * 将操作数栈顶元素作为数字输出。
     */
    void printNumber();

    /**
     * 读入一个字符，并将其存到栈顶元素所表示的地址的堆空间中。
     */
    void readChar();

    /**
     * 读入一个数字，并将其存到栈顶元素所表示的地址的堆空间中。
     *
     */
    void readNumber();

}
