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
 * Whitespace的虚拟机模型。虚拟机拥有一个操作数栈、堆内存、处理器以及IO设备。
 *
 * @author Azige
 */
public interface WhitespaceVM{

    /**
     * 获得操作数栈
     *
     * @return 操作数栈
     */
    OperandStack getOperandStack();

    /**
     * 获得堆内存
     *
     * @return 堆内存
     */
    HeapMemory getHeapMemory();

    /**
     * 获得IO设备
     *
     * @return IO设备
     */
    IODevice getIODevice();

    /**
     * 获得处理器
     *
     * @return 处理器
     */
    Processor getProcessor();
}
