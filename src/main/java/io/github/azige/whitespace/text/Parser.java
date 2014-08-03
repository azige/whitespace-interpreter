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

import java.io.IOException;

import io.github.azige.whitespace.command.Command;

/**
 * Whitespace语法解析器。
 *
 * @author Azige
 */
public interface Parser extends AutoCloseable{

    /**
     * 获得下一条指令。
     *
     * @return 下一条指令，如果已经到达流末尾则为null
     */
    Command next();

    @Override
    void close() throws IOException;
}
