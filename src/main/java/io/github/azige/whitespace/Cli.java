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

import java.io.BufferedReader;

import io.github.azige.whitespace.text.PseudoCodeGenerator;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import io.github.azige.whitespace.command.Program;
import io.github.azige.whitespace.szm.DefaultSzmCommandFactory;
import io.github.azige.whitespace.szm.SzmCommandFactory;
import io.github.azige.whitespace.szm.SzmParser;
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
    static final String DEFAULT_ENCODING = "UTF-8";
    static Charset encoding;
    static boolean useSzm = false;

    public static void main(String[] args){
        Options options = new Options()
            .addOption("h", "help", false, "打印此消息")
            .addOptionGroup(new OptionGroup()
                .addOption(new Option("p", "不运行程序，而是将源程序翻译成可阅读的伪代码"))
                .addOption(new Option("c", "不运行程序，而是将源程序编译为二进制文件"))
            )
            .addOption(null, "szm", false, "使用水沝淼语言扩展，输入的源程序将作为水沝淼语言解释")
            .addOption("e", "encoding", true, "指定源文件编码，默认使用" + DEFAULT_ENCODING);

        try{
            CommandLineParser parser = new BasicParser();
            CommandLine cl = parser.parse(options, args);

            if (cl.hasOption('h')){
                printHelp(System.out, options);
                return;
            }

            if (cl.hasOption('e')){
                encoding = Charset.forName(cl.getOptionValue('e'));
            }else{
                encoding = Charset.forName(DEFAULT_ENCODING);
            }

            if (cl.hasOption("szm")){
                useSzm = true;
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
            ex.printStackTrace();
            printHelp(System.err, options);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    static void execute(InputStream input, String path) throws IOException, ClassNotFoundException{
        WhitespaceVM vm = new DefaultWhitespaceVM();
        Program program;
        if (path.endsWith(WHITESPACE_BINARY_FILE_SUFFIX)){
            ObjectInputStream oinput = new ObjectInputStream(new GZIPInputStream(input));
            program = (Program)oinput.readObject();
        }else{
            Interpreter interpreter = createInterpreter();
            program = interpreter.interpret(createReader(input));
        }
        vm.getProcessor().loadProgram(program);
        vm.getProcessor().executeAll(true);
    }

    static void compile(InputStream input, String path) throws IOException{
        Interpreter interpreter = createInterpreter();
        Program program = interpreter.interpret(createReader(input));
        path = path.replaceFirst("\\.[^\\.]+$", WHITESPACE_BINARY_FILE_SUFFIX);
        try (ObjectOutputStream output = new ObjectOutputStream(new GZIPOutputStream(Files.newOutputStream(Paths.get(path))))){
            output.writeObject(program);
        }
    }

    static void printPseudoCode(InputStream input){
        PseudoCodeGenerator generator = new PseudoCodeGenerator(System.out);
        if (useSzm){
            generator.translate(new SzmParser(createReader(input), new DefaultSzmCommandFactory()));
        }else{
            generator.translate(createReader(input));
        }
    }

    static Interpreter createInterpreter(){
        if (useSzm){
            return new Interpreter(new DefaultSzmCommandFactory(),
                (reader, cf) -> new SzmParser(reader, (SzmCommandFactory)cf));
        }else{
            return new Interpreter();
        }
    }

    static Reader createReader(InputStream input){
        return new BufferedReader(new InputStreamReader(input, encoding));
    }

    static void printHelp(PrintStream out, Options options){
        HelpFormatter hf = new HelpFormatter();
        PrintWriter pw = new PrintWriter(out);
        hf.printHelp(pw, hf.getWidth(), "whitespace [-c|-p] <source file>",
            "将指定的文件作为Whitespace源代码执行。", options, hf.getLeftPadding(), hf.getDescPadding(), null);
        pw.flush();
    }
}
