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

import io.github.azige.whitespace.text.PseudoCodeGenerator;
import io.github.azige.whitespace.vm.DefaultWhitespaceVM;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.io.PrintWriter;

import io.github.azige.whitespace.vm.WhitespaceVM;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * 命令行程序主类。<br>
 * 接受单个参数并将参数对应的文件作为Whitespace源程序解释执行。
 *
 * @author Azige
 */
public class Cli{

    public static void main(String[] args){
        Options options = new Options()
            .addOption("h", "help", false, "打印此消息")
            .addOption("p", false, "不运行程序，而是将源程序翻译成可阅读的伪代码");

        try{
            CommandLineParser parser = new BasicParser();
            CommandLine cl = parser.parse(options, args);

            if (cl.hasOption('h')){
                printHelp(System.out, options);
                return;
            }

            String[] fileArgs = cl.getArgs();
            if (fileArgs.length != 1){
                printHelp(System.err, options);
                return;
            }

            if (cl.hasOption('p')){
                PseudoCodeGenerator generator = new PseudoCodeGenerator(System.out);
                generator.translate(new FileReader(fileArgs[0]));
            }else{
                WhitespaceVM vm = new DefaultWhitespaceVM();
                Interpreter interpreter = new Interpreter(vm);
                interpreter.interpret(new FileReader(new File(fileArgs[0])));
                interpreter.run();
            }
        }catch (ParseException ex){
            System.err.println(ex.getLocalizedMessage());
            printHelp(System.err, options);
        }catch (FileNotFoundException ex){
            System.err.println(ex.getLocalizedMessage());
        }
    }

    static void printHelp(PrintStream out, Options options){
        HelpFormatter hf = new HelpFormatter();
        PrintWriter pw = new PrintWriter(out);
        hf.printHelp(pw, hf.getWidth(), "whitespace <source file>",
            "将指定的文件作为Whitespace源代码执行。", options, hf.getLeftPadding(), hf.getDescPadding(), null);
        pw.flush();
    }
}
