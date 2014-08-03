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

import io.github.azige.whitespace.vm.WhitespaceVM;

/**
 * 水沝淼虚拟机，扩展了可以获得程序退出代码的方法。
 *
 * @author Azige
 */
public interface WaterVM extends WhitespaceVM{

    /**
     * 获得上一次运行的程序的退出代码。
     *
     * @return 上一次运行的程序的退出代码，如果没有运行过程序则为0
     */
    int getLastExitCode();
}
