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
import java.util.function.BinaryOperator;

/**
 * Arithmetic的实现类。
 * @author Azige
 */
public class ArithmeticImpl implements Arithmetic{

    private final WhitespaceVM vm;

    ArithmeticImpl(WhitespaceVM vm){
        this.vm = vm;
    }

    @Override
    public void add(){
        vm.getOpStack().push(doOpt((l, r) -> l.add(r)));
    }

    @Override
    public void sub(){
        vm.getOpStack().push(doOpt((l, r) -> l.subtract(r)));
    }

    @Override
    public void mul(){
        vm.getOpStack().push(doOpt((l, r) -> l.multiply(r)));
    }

    @Override
    public void div(){
        vm.getOpStack().push(doOpt((l, r) -> l.divide(r)));
    }

    @Override
    public void mod(){
        vm.getOpStack().push(doOpt((l, r) -> l.mod(r)));
    }

    private BigInteger doOpt(BinaryOperator<BigInteger> opt){
        BigInteger right = vm.getOpStack().pop();
        BigInteger left = vm.getOpStack().pop();
        return opt.apply(left, right);
    }
}
