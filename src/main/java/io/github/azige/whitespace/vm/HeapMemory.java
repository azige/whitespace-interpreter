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
import java.util.Map;

/**
 * Whitespace虚拟机专用的堆内存。此内存是离散的地址与数据的映射，其中，地址和数据都是BigInteger。<br>
 *
 * @author Azige
 */
public interface HeapMemory{

    /**
     * 将数据存放到指定的地址
     *
     * @param address 要访问的地址
     * @param data 要存储的数据
     */
    void store(BigInteger address, BigInteger data);

    /**
     * 从指定的地址取回数据
     *
     * @param address 要访问的地址
     * @return 对应的数据
     */
    BigInteger retrieve(BigInteger address);

    /**
     * 释放指定的地址的空间
     *
     * @param address 要访问的地址
     * @return 指定的地址所对应的数据
     */
    BigInteger free(BigInteger address);

    /**
     * 检查指定的地址是否可用。
     *
     * @param address 要访问的地址
     * @return 如果指定的地址存储了数据并且没有被释放则为true
     */
    boolean isAvailable(BigInteger address);

    /**
     * 获得此对象的<b>不可变</b>映射的表示形式。
     *
     * @return 对应此对象的映射
     */
    Map<BigInteger, BigInteger> toMap();
}
