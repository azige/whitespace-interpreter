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
 * 定义了Whitespace中的堆操作指令的接口。<br>
 * 堆操作指令的IMP为[Tab][Tab]。<br>
 * 堆的实现也由实现类提供。
 *
 * @author Azige
 */
public interface HeapAccess{

    /**
     * 从操作数栈中读入数据与地址，并将数据存到对应的地址里。先入栈的为地址，后入栈的为数据。
     */
    void store();

    /**
     * 从操作数栈中读入地址，并读出地址对应的数据放到操作数栈顶。
     */
    void retrieve();

}
