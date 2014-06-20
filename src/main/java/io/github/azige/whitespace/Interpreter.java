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

import io.github.azige.whitespace.vm.WhitespaceVMImpl;
import io.github.azige.whitespace.vm.WhitespaceVM;

import java.io.Reader;
import java.io.StringReader;

/**
 * Whitespace的解释器。<br>
 * 此类读入Whitespace源代码，将其进行解释后生成指令序列。
 *
 * @author Azige
 */
public class Interpreter{

    private final WhitespaceVM vm;

    public Interpreter(){
        this(new WhitespaceVMImpl());
    }

    public Interpreter(WhitespaceVM vm){
        this.vm = vm;
    }

    public void interpret(String script){
        interpret(new StringReader(script));
    }

    public void interpret(Reader input){
        WhitespaceParser parser = new WhitespaceParser(input);
        while (parser.hasNext()){
            vm.getExecutor().addCommand(parser.next());
        }
    }

    public void run(){
        vm.getExecutor().run();
    }
}
