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
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import io.github.azige.whitespace.WhitespaceException;

/**
 * 操作数栈的默认实现，此类使用{@link LinkedList}来实现栈。
 *
 * @author Azige
 */
public class DefaultOperandStack implements OperandStack{

    private final LinkedList<BigInteger> list = new LinkedList<>();

    @Override
    public void push(BigInteger number){
        list.push(number);
    }

    @Override
    public BigInteger pop(){
        checkEmpty();
        return list.pop();
    }

    @Override
    public BigInteger peek(){
        checkEmpty();
        return list.peek();
    }

    @Override
    public BigInteger peek(int index){
        checkBound(index);
        return list.get(index);
    }

    @Override
    public void dup(){
        checkEmpty();
        push(peek());
    }

    @Override
    public void dup(int index){
        checkBound(index);
        push(peek(index));
    }

    @Override
    public BigInteger remove(int index){
        checkBound(index);
        return list.remove(index);
    }

    @Override
    public void swap(){
        checkBound(1);
        push(remove(1));
    }

    @Override
    public List<BigInteger> toList(){
        return Collections.unmodifiableList(list);
    }

    private void checkEmpty(){
        if (list.isEmpty()){
            throw new WhitespaceException("栈为空");
        }
    }

    private void checkBound(int index){
        if (index < 0 || index >= list.size()){
            throw new WhitespaceException("索引越界：" + index);
        }
    }
}
