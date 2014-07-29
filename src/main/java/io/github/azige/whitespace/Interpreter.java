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

import io.github.azige.whitespace.vm.DefaultWhitespaceVM;
import io.github.azige.whitespace.vm.WhitespaceVM;

import java.io.Reader;
import java.io.StringReader;

import io.github.azige.whitespace.command.Command;
import io.github.azige.whitespace.command.CommandFactory;
import io.github.azige.whitespace.command.DefaultCommandFactory;
import io.github.azige.whitespace.command.Program;
import io.github.azige.whitespace.text.Parser;

/**
 * Whitespace的解释器。<br>
 * 此类读入Whitespace源代码，将其进行解释后生成指令序列。
 *
 * @author Azige
 */
public class Interpreter{

    private final WhitespaceVM vm;
    private final CommandFactory cf;

    public Interpreter(){
        this(new DefaultWhitespaceVM(), new DefaultCommandFactory());
    }

    public Interpreter(WhitespaceVM vm){
        this(vm, new DefaultCommandFactory());
    }

    public Interpreter(WhitespaceVM vm, CommandFactory cf){
        this.vm = vm;
        this.cf = cf;
    }

    public void interpret(String code){
        interpret(new StringReader(code));
    }

    public void interpret(Reader input){
        Parser parser = new Parser(cf, input);
        Program.Builder builder = new Program.Builder();
        Command command;
        while ((command = parser.next()) != null){
            builder.addCommand(command);
        }
        vm.getProcessor().loadProgram(builder.build());
    }

    public void run(){
        vm.getProcessor().executeAll(true);
    }

    public WhitespaceVM getVM(){
        return vm;
    }

    public CommandFactory getCommandFactory(){
        return cf;
    }
}
