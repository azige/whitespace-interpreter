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
import java.util.Map;

/**
 * HeapAccess的实现类。
 * @author Azige
 */
public class HeapAccessImpl implements HeapAccess{

    private final WhitespaceVM vm;

    HeapAccessImpl(WhitespaceVM vm){
        this.vm = vm;
    }

    @Override
    public void store(){
        BigInteger value = vm.getOpStack().pop();
        BigInteger address = vm.getOpStack().pop();
        vm.getHeap().put(address, value);
    }

    @Override
    public void retrieve(){
        BigInteger address = vm.getOpStack().pop();
        BigInteger value = vm.getHeap().get(address);
        if (value == null){
            value = BigInteger.ZERO;
        }
        vm.getOpStack().push(value);
    }
}
