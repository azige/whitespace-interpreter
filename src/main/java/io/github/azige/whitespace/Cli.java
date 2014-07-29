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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import io.github.azige.whitespace.command.Program;
import io.github.azige.whitespace.vm.DefaultWhitespaceVM;
import io.github.azige.whitespace.vm.WhitespaceVM;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * 命令行程序主类。<br>
 * 接受单个参数并将参数对应的文件作为Whitespace源程序解释执行。
 *
 * @author Azige
 */
public class Cli{

    static final String WHITESPACE_BINARY_FILE_SUFFIX = ".wso";

    public static void main(String[] args){
        Options options = new Options()
            .addOption("h", "help", false, "打印此消息")
            .addOptionGroup(new OptionGroup()
                .addOption(new Option("p", "不运行程序，而是将源程序翻译成可阅读的伪代码"))
                .addOption(new Option("c", "不运行程序，而是将源程序编译为二进制文件"))
            );

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

            try (InputStream input = Files.newInputStream(Paths.get(fileArgs[0]))){
                if (cl.hasOption('p')){
                    printPseudoCode(input);
                }else if (cl.hasOption('c')){
                    compile(input, fileArgs[0]);
                }else{
                    execute(input, fileArgs[0]);
                }
            }
        }catch (ParseException ex){
            System.err.println(ex.getLocalizedMessage());
            printHelp(System.err, options);
        }catch (Exception ex){
            System.err.println(ex.getLocalizedMessage());
        }
    }

    static void execute(InputStream input, String path) throws IOException, ClassNotFoundException{
        WhitespaceVM vm = new DefaultWhitespaceVM();
        Program program;
        if (path.endsWith(WHITESPACE_BINARY_FILE_SUFFIX)){
            ObjectInputStream oinput = new ObjectInputStream(new GZIPInputStream(input));
            program = (Program)oinput.readObject();
        }else{
            Interpreter interpreter = new Interpreter();
            program = interpreter.interpret(new InputStreamReader(input));
        }
        vm.getProcessor().loadProgram(program);
        vm.getProcessor().executeAll(true);
    }

    static void compile(InputStream input, String path) throws IOException{
        Interpreter interpreter = new Interpreter();
        Program program = interpreter.interpret(new InputStreamReader(input));
        path = path.replaceFirst("\\.[^\\.]+$", WHITESPACE_BINARY_FILE_SUFFIX);
        try (ObjectOutputStream output = new ObjectOutputStream(new GZIPOutputStream(Files.newOutputStream(Paths.get(path))))){
            output.writeObject(program);
        }
    }

    static void printPseudoCode(InputStream input){
        PseudoCodeGenerator generator = new PseudoCodeGenerator(System.out);
        generator.translate(new InputStreamReader(input));
    }

    static void printHelp(PrintStream out, Options options){
        HelpFormatter hf = new HelpFormatter();
        PrintWriter pw = new PrintWriter(out);
        hf.printHelp(pw, hf.getWidth(), "whitespace [-c|-p] <source file>",
            "将指定的文件作为Whitespace源代码执行。", options, hf.getLeftPadding(), hf.getDescPadding(), null);
        pw.flush();
    }
}
