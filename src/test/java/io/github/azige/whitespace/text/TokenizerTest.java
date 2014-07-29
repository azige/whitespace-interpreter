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

package io.github.azige.whitespace.text;

import static org.junit.Assert.*;

import java.io.InputStreamReader;
import java.math.BigInteger;

import org.junit.Test;

public class TokenizerTest{

    @Test
    public void testSomething(){
        Tokenizer tokenizer = new Tokenizer(new InputStreamReader(getClass().getResourceAsStream("/1to10.ws")));
        Token token;
        assertEquals(Token.Type.S_PUSH, tokenizer.next().getType());
        token = tokenizer.next();
        assertEquals(Token.Type.NUMBER, token.getType());
        assertEquals(BigInteger.valueOf(1), token.getNumber());
        assertEquals(Token.Type.F_MARK, tokenizer.next().getType());
        assertEquals(Token.Type.LABEL, tokenizer.next().getType());
        assertEquals(Token.Type.S_DUP, tokenizer.next().getType());
        assertEquals(Token.Type.I_PNUM, tokenizer.next().getType());
        assertEquals(Token.Type.S_PUSH, tokenizer.next().getType());
        token = tokenizer.next();
        assertEquals(Token.Type.NUMBER, token.getType());
        assertEquals(BigInteger.valueOf(10), token.getNumber());
        assertEquals(Token.Type.I_PCHAR, tokenizer.next().getType());
        assertEquals(Token.Type.S_PUSH, tokenizer.next().getType());
        token = tokenizer.next();
        assertEquals(Token.Type.NUMBER, token.getType());
        assertEquals(BigInteger.valueOf(1), token.getNumber());
        assertEquals(Token.Type.A_ADD, tokenizer.next().getType());
        assertEquals(Token.Type.S_DUP, tokenizer.next().getType());
        assertEquals(Token.Type.S_PUSH, tokenizer.next().getType());
        token = tokenizer.next();
        assertEquals(Token.Type.NUMBER, token.getType());
        assertEquals(BigInteger.valueOf(11), token.getNumber());
        assertEquals(Token.Type.A_SUB, tokenizer.next().getType());
        assertEquals(Token.Type.F_JUMPN, tokenizer.next().getType());
        assertEquals(Token.Type.LABEL, tokenizer.next().getType());
        assertNull(tokenizer.next());
    }
}
