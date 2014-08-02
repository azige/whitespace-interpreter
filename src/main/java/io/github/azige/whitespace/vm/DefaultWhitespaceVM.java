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

import java.util.Objects;

/**
 * WhitespaceVM的默认实现。
 *
 * @author Azige
 */
public class DefaultWhitespaceVM implements WhitespaceVM{

    private final OperandStack operandStack;
    private final HeapMemory heapMemory;
    private final IODevice iODevice;
    private final Processor processor;

    private Processor createProcessor(){
        return new AbstractProcessor(){

            @Override
            protected WhitespaceVM getVM(){
                return DefaultWhitespaceVM.this;
            }
        };
    }

    /**
     * 构造默认的虚拟机
     */
    public DefaultWhitespaceVM(){
        this.operandStack = new DefaultOperandStack();
        this.heapMemory = new DefaultHeapMemory();
        this.iODevice = new DefaultIODevice();
        this.processor = createProcessor();
    }

    /**
     * 使用指定的组件构造虚拟机。
     *
     * @param operandStack 操作数栈
     * @param heapMemory 堆内存
     * @param iODevice IO设备
     * @param processor 处理器
     * @throws NullPointerException 如果任何参数为null
     */
    public DefaultWhitespaceVM(OperandStack operandStack, HeapMemory heapMemory, IODevice iODevice, Processor processor){
        this.operandStack = Objects.requireNonNull(operandStack);
        this.heapMemory = Objects.requireNonNull(heapMemory);
        this.iODevice = Objects.requireNonNull(iODevice);
        this.processor = Objects.requireNonNull(processor);
    }

    @Override
    public OperandStack getOperandStack(){
        return operandStack;
    }

    @Override
    public HeapMemory getHeapMemory(){
        return heapMemory;
    }

    @Override
    public IODevice getIODevice(){
        return iODevice;
    }

    @Override
    public Processor getProcessor(){
        return processor;
    }
}
