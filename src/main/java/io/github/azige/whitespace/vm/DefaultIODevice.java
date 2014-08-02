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

import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

/**
 * IO设备的默认实现。
 *
 * @author Azige
 */
public class DefaultIODevice implements IODevice{

    private Reader input = new InputStreamReader(System.in);
    private PrintWriter output = new PrintWriter(System.out, true);

    @Override
    public Reader getInput(){
        return input;
    }

    @Override
    public void setInput(Reader input){
        this.input = input;
    }

    @Override
    public PrintWriter getOutput(){
        return output;
    }

    @Override
    public void setOutput(Writer output){
        if (output instanceof PrintWriter){
            this.output = (PrintWriter)output;
        }else{
            this.output = new PrintWriter(output, true);
        }
    }
}
