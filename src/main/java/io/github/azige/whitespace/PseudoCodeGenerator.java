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

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;

import io.github.azige.whitespace.command.Command;

/**
 *
 * @author Azige
 */
public class PseudoCodeGenerator{

    private final PrintStream out;

    public PseudoCodeGenerator(OutputStream out){
        if (out instanceof PrintStream){
            this.out = (PrintStream)out;
        }else{
            this.out = new PrintStream(out);
        }
    }

    public void translate(Reader input){
        WhitespaceParser parser = new WhitespaceParser(input);
        while (parser.hasNext()){
            Command<?> command = parser.next();
            out.println(command);
        }
    }
}
