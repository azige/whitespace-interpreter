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

package io.github.azige.whitespace.szm;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.Charset;

import io.github.azige.whitespace.Interpreter;
import io.github.azige.whitespace.command.Program;
import io.github.azige.whitespace.vm.DefaultWhitespaceVM;
import io.github.azige.whitespace.vm.WhitespaceVM;

/**
 *
 * @author Azige
 */
public class SzmTest{

    public SzmTest(){
    }

    @BeforeClass
    public static void setUpClass(){
    }

    @AfterClass
    public static void tearDownClass(){
    }

    @Before
    public void setUp(){
    }

    @After
    public void tearDown(){
    }

    @Test
    public void testSomething(){
        WhitespaceVM vm = new DefaultWhitespaceVM();
        StringWriter output = new StringWriter();
        vm.getIODevice().setOutput(output);
        Interpreter interpreter = new Interpreter(new DefaultSzmCommandFactory(), (input, cf) -> new SzmParser(input, (SzmCommandFactory)cf));
        Program program = interpreter.interpret(new InputStreamReader(getClass().getResourceAsStream("/szm/0to9.szm"), Charset.forName("UTF-8")));
        vm.getProcessor().loadProgram(program);
        vm.getProcessor().executeAll(true);
        assertEquals("0\n1\n2\n3\n4\n5\n6\n7\n8\n9\n", output.toString());
    }
}
