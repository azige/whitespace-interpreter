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
import java.util.function.BiFunction;

import io.github.azige.whitespace.command.Command;
import io.github.azige.whitespace.command.CommandFactory;
import io.github.azige.whitespace.command.DefaultCommandFactory;
import io.github.azige.whitespace.command.Program;
import io.github.azige.whitespace.text.DefaultParser;
import io.github.azige.whitespace.text.Parser;

/**
 * Whitespace的解释器。<br>
 * 此类将Whitespace源代码转换为Whitespace程序对象。
 *
 * @author Azige
 */
public class Interpreter{

    private final CommandFactory cf;
    private final BiFunction<Reader, CommandFactory, Parser> parserFactory;

    /**
     * 使用默认的命令工厂构造对象。
     */
    public Interpreter(){
        this(new DefaultCommandFactory(), DefaultParser::new);
    }

    /**
     * 使用指定的命令工厂构造对象。
     *
     * @param cf 用于构造此对象的指令工厂
     */
    public Interpreter(CommandFactory cf){
        this(cf, DefaultParser::new);
    }

    /**
     * 使用指定的命令工厂和解析器工厂方法构造对象。
     *
     * @param cf
     * @param parserFactory
     */
    public Interpreter(CommandFactory cf, BiFunction<Reader, CommandFactory, Parser> parserFactory){
        this.cf = cf;
        this.parserFactory = parserFactory;
    }

    /**
     * 从字符串源解释程序。
     *
     * @param code 源程序代码
     * @return 由源程序代码解释生成的程序对象
     */
    public Program interpret(String code){
        return interpret(new StringReader(code));
    }

    /**
     * 从输入流解释程序。
     *
     * @param input 用于读入源程序的输入流
     * @return 由源程序代码解释生成的程序对象
     */
    public Program interpret(Reader input){
        Parser parser = parserFactory.apply(input, cf);
        Program.Builder builder = new Program.Builder();
        Command command;
        while ((command = parser.next()) != null){
            builder.addCommand(command);
        }

        return builder.build();
    }

    /**
     * 获得与此对象关联的命令工厂。
     *
     * @return 与此对象关联的命令工厂
     */
    public CommandFactory getCommandFactory(){
        return cf;
    }
}
