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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import io.github.azige.whitespace.command.CommandListExecutor;

/**
 * WhitespaceVM的实现。
 * @author Azige
 */
public class WhitespaceVMImpl implements WhitespaceVM{

    private final LinkedList<BigInteger> opStack = new LinkedList<>();
    private final Map<BigInteger, BigInteger> heap = new HashMap<>();
    private final CommandListExecutor executor;
    private final StackManipulation stackManipulation;
    private final Arithmetic arithmetic;
    private final HeapAccess heapAccess;
    private final FlowControl flowControl;
    private final IOImpl io;

    public WhitespaceVMImpl(){
        this.executor = new CommandListExecutor(this);
        this.stackManipulation = new StackManipulationImpl(this);
        this.arithmetic = new ArithmeticImpl(this);
        this.heapAccess = new HeapAccessImpl(this);
        this.flowControl = new FlowControlImpl(this);
        this.io = new IOImpl(this);
    }

    @Override
    public LinkedList<BigInteger> getOpStack(){
        return opStack;
    }

    @Override
    public Map<BigInteger, BigInteger> getHeap(){
        return heap;
    }

    @Override
    public CommandListExecutor getExecutor(){
        return executor;
    }

    @Override
    public StackManipulation getStackManipulation(){
        return stackManipulation;
    }

    @Override
    public Arithmetic getArithmetic(){
        return arithmetic;
    }

    @Override
    public HeapAccess getHeapAccess(){
        return heapAccess;
    }

    @Override
    public FlowControl getFlowControl(){
        return flowControl;
    }

    @Override
    public IOImpl getIO(){
        return io;
    }
}
