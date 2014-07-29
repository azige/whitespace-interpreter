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

import java.io.Reader;
import java.io.StringReader;

import io.github.azige.whitespace.command.Command;
import io.github.azige.whitespace.command.CommandFactory;
import io.github.azige.whitespace.command.DefaultCommandFactory;
import io.github.azige.whitespace.command.Program;
import io.github.azige.whitespace.text.Parser;

/**
 * Whitespace的解释器。<br>
 * 此类将Whitespace源代码转换为Whitespace程序对象。
 *
 * @author Azige
 */
public class Interpreter{

    private final CommandFactory cf;

    public Interpreter(){
        this(new DefaultCommandFactory());
    }

    public Interpreter(CommandFactory cf){
        this.cf = cf;
    }

    public Program interpret(String code){
        return interpret(new StringReader(code));
    }

    public Program interpret(Reader input){
        Parser parser = new Parser(cf, input);
        Program.Builder builder = new Program.Builder();
        Command command;
        while ((command = parser.next()) != null){
            builder.addCommand(command);
        }

        return builder.build();
    }

    public CommandFactory getCommandFactory(){
        return cf;
    }
}
