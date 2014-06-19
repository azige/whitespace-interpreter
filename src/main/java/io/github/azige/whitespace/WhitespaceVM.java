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
package io.github.azige.whitespace;

import java.math.BigInteger;
import java.util.LinkedList;

/**
 * Whitespace的虚拟机模型。
 * 虚拟机拥有一个操作数栈，并接受五种类的指令，即{@link Arithmetic}接口代表的数学运算，
 * {@link FlowControl}接口代表的流程控制，{@link HeapAccess}接口代表的堆操作，
 * {@link IO}接口代表的IO操作，以及{@link StackManipulation}接口代表的栈操作。
 * @author Azige
 */
public interface WhitespaceVM{

    /**
     * 获得操作数栈
     * @return 操作数栈
     */
    LinkedList<BigInteger> getOpStack();

    /**
     * 获得数学运算操作对象
     * @return 数学运算操作对象
     */
    Arithmetic getArithmetic();

    /**
     * 获得流程控制对象
     * @return 流程控制对象
     */
    FlowControl getFlowControl();

    /**
     * 获得堆操作对象
     * @return 堆操作对象
     */
    HeapAccess getHeapAccess();

    /**
     * 获得IO操作对象
     * @return IO操作对象
     */
    IO getIO();

    /**
     * 获得栈操作对象
     * @return 栈操作对象
     */
    StackManipulation getStackManipulation();
}
