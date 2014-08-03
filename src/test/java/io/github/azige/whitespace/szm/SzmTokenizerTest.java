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
import java.io.StringReader;
import java.nio.charset.Charset;

/**
 *
 * @author Azige
 */
public class SzmTokenizerTest{

    public SzmTokenizerTest(){
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
    public void testSomeMethod(){
        SzmTokenizer tokenizer = new SzmTokenizerImpl(new InputStreamReader(getClass().getResourceAsStream("/szm/0to9.szm"), Charset.forName("UTF-8")));
        assertEquals(SzmToken.Type.S_PUSH, tokenizer.next().getType());
        assertEquals(SzmToken.Type.NUMBER, tokenizer.next().getType());
        assertEquals(SzmToken.Type.V_STORE, tokenizer.next().getType());
        assertEquals(SzmToken.Type.NUMBER, tokenizer.next().getType());
        assertEquals(SzmToken.Type.F_MARK, tokenizer.next().getType());
        assertEquals(SzmToken.Type.NUMBER, tokenizer.next().getType());
        assertEquals(SzmToken.Type.V_LOAD, tokenizer.next().getType());
        assertEquals(SzmToken.Type.NUMBER, tokenizer.next().getType());
        assertEquals(SzmToken.Type.S_PUSH, tokenizer.next().getType());
        assertEquals(SzmToken.Type.NUMBER, tokenizer.next().getType());
        assertEquals(SzmToken.Type.A_SUB, tokenizer.next().getType());
        assertEquals(SzmToken.Type.F_JUMPZ, tokenizer.next().getType());
        assertEquals(SzmToken.Type.NUMBER, tokenizer.next().getType());
        assertEquals(SzmToken.Type.V_LOAD, tokenizer.next().getType());
        assertEquals(SzmToken.Type.NUMBER, tokenizer.next().getType());
        assertEquals(SzmToken.Type.I_PNUM, tokenizer.next().getType());
        assertEquals(SzmToken.Type.S_PUSH, tokenizer.next().getType());
        assertEquals(SzmToken.Type.NUMBER, tokenizer.next().getType());
        assertEquals(SzmToken.Type.I_PCHAR, tokenizer.next().getType());
        assertEquals(SzmToken.Type.V_LOAD, tokenizer.next().getType());
        assertEquals(SzmToken.Type.NUMBER, tokenizer.next().getType());
        assertEquals(SzmToken.Type.S_PUSH, tokenizer.next().getType());
        assertEquals(SzmToken.Type.NUMBER, tokenizer.next().getType());
        assertEquals(SzmToken.Type.A_ADD, tokenizer.next().getType());
        assertEquals(SzmToken.Type.V_STORE, tokenizer.next().getType());
        assertEquals(SzmToken.Type.NUMBER, tokenizer.next().getType());
        assertEquals(SzmToken.Type.F_JUMP, tokenizer.next().getType());
        assertEquals(SzmToken.Type.NUMBER, tokenizer.next().getType());
        assertEquals(SzmToken.Type.F_MARK, tokenizer.next().getType());
        assertEquals(SzmToken.Type.NUMBER, tokenizer.next().getType());
        assertEquals(SzmToken.Type.F_RETURN, tokenizer.next().getType());
    }

}
