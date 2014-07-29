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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.InputStreamReader;

import io.github.azige.whitespace.command.CommandType;
import io.github.azige.whitespace.command.DefaultCommandFactory;

public class ParserTest{

    @Test
    public void testSomeMethod(){
        Parser parser = new Parser(new DefaultCommandFactory(), new InputStreamReader(getClass().getResourceAsStream("/1to10.ws")));
        assertEquals(CommandType.S_PUSH, parser.next().getType());
        assertEquals(CommandType.F_MARK, parser.next().getType());
        assertEquals(CommandType.S_DUP, parser.next().getType());
        assertEquals(CommandType.I_PNUM, parser.next().getType());
        assertEquals(CommandType.S_PUSH, parser.next().getType());
        assertEquals(CommandType.I_PCHAR, parser.next().getType());
        assertEquals(CommandType.S_PUSH, parser.next().getType());
        assertEquals(CommandType.A_ADD, parser.next().getType());
        assertEquals(CommandType.S_DUP, parser.next().getType());
        assertEquals(CommandType.S_PUSH, parser.next().getType());
        assertEquals(CommandType.A_SUB, parser.next().getType());
        assertEquals(CommandType.F_JUMPN, parser.next().getType());
    }

}
