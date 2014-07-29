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
package io.github.azige.whitespace.vm;

import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

/**
 * Whitespace虚拟机专用的IO设备。虚拟机仅有单一的输入源和输出源。Whitespace中仅定义了字符的IO操作，故使用字符流。
 *
 * @author Azige
 */
public interface IODevice{

    Reader getInput();

    void setInput(Reader input);

    PrintWriter getOutput();

    void setOutput(Writer output);
}
