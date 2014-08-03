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
package io.github.azige.whitespace.szm;

import java.io.IOException;

/**
 * 水沝淼词法分析器，从文本中提取记号流。
 *
 * @author Azige
 */
public interface SzmTokenizer extends AutoCloseable{

    /**
     * 获得下一个记号。
     *
     * @return 下一个记号，如果已经没有更多的记号则为null
     */
    SzmToken next();

    @Override
    void close() throws IOException;
}
