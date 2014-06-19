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

import java.io.File;
import java.io.FileReader;

/**
 * 命令行程序主类。<br>
 * 接受单个参数并将参数对应的文件作为Whitespace源程序解释执行。
 *
 * @author Azige
 */
public class Cli{

    public static void main(String[] args) throws Exception{
        if (args.length != 1){
            System.err.println("Usage: whitespace input_file");
            return;
        }
        WhitespaceVM vm = new WhitespaceVMImpl();
        Interpreter interpreter = new Interpreter(vm);
        interpreter.interpret(new FileReader(new File(args[0])));
        interpreter.run();
    }
}
