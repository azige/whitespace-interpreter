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

import io.github.azige.whitespace.WhitespaceException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.math.BigInteger;

/**
 * IO的实现类。
 * @author Azige
 */
public class IOImpl implements IO{

    private final WhitespaceVM vm;
    private final BufferedReader in;
    private final PrintStream out;

    IOImpl(WhitespaceVM vm){
        this(vm, System.in, System.out);
    }

    IOImpl(WhitespaceVM vm, InputStream in, PrintStream out){
        this.vm = vm;
        this.in = new BufferedReader(new InputStreamReader(in));
        this.out = out;
    }

    @Override
    public void printChar(){
        int codePoint = vm.getOpStack().pop().intValueExact();
        out.print(Character.toChars(codePoint));
    }

    @Override
    public void printNumber(){
        out.print(vm.getOpStack().pop());
    }

    @Override
    public void readChar(){
        try{
            vm.getOpStack().push(BigInteger.valueOf(in.read()));
            vm.getHeapAccess().store();
        }catch (IOException ex){
            throw new WhitespaceException(ex);
        }
    }

    @Override
    public void readNumber(){
        try{
            StringBuilder sb = new StringBuilder();
            boolean startFlag = false;
            while (true){
                int c = in.read();
                if (c == -1){
                    break;
                }else if (Character.isWhitespace(c)){
                    if (startFlag){
                        break;
                    }
                }else{
                    startFlag = true;
                    sb.append((char)c);
                }
            }
            vm.getOpStack().push(new BigInteger(sb.toString()));
            vm.getHeapAccess().store();
        }catch (IOException ex){
            throw new WhitespaceException(ex);
        }
    }
}
