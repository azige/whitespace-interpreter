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
import java.util.HashMap;
import java.util.Map;

import io.github.azige.whitespace.WhitespaceException;

/**
 * 堆内存的默认实现，此类使用{@link HashMap}来映射地址与数据。
 *
 * @author Azige
 */
public class DefaultHeapMemory implements HeapMemory{

    private final Map<BigInteger, BigInteger> map = new HashMap<>();

    @Override
    public void store(BigInteger address, BigInteger data){
        map.put(address, data);
    }

    @Override
    public BigInteger retrieve(BigInteger address){
        checkAddress(address);
        return map.get(address);
    }

    @Override
    public BigInteger free(BigInteger address){
        checkAddress(address);
        return map.remove(address);
    }

    @Override
    public Map<BigInteger, BigInteger> toMap(){
        return Collections.unmodifiableMap(map);
    }

    @Override
    public boolean isAvailable(BigInteger address){
        return map.containsKey(address);
    }

    private void checkAddress(BigInteger address){
        if (!isAvailable(address)){
            throw new WhitespaceException("地址不可用：" + address);
        }
    }
}
