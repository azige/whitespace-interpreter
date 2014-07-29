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

import org.junit.Test;

import static org.junit.Assert.*;

import java.io.InputStreamReader;
import java.io.StringWriter;

import io.github.azige.whitespace.vm.DefaultWhitespaceVM;
import io.github.azige.whitespace.vm.WhitespaceVM;

public class InterpreterTest{

    @Test
    public void testSomeMethod(){
        WhitespaceVM vm = new DefaultWhitespaceVM();
        StringWriter output = new StringWriter();
        vm.getIODevice().setOutput(output);
        Interpreter interpreter = new Interpreter(vm);
        interpreter.interpret(new InputStreamReader(getClass().getResourceAsStream("/1to10.ws")));
        interpreter.run();
        assertEquals("1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n", output.toString());
    }

}
