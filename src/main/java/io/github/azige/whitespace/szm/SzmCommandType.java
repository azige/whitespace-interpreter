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

import io.github.azige.whitespace.command.Command;

/**
 * 水沝淼语言追加的指令类型。
 *
 * @author Azige
 */
public enum SzmCommandType implements Command.Type{

    V_LOAD,
    V_STORE,
    V_GLOAD,
    V_GSTORE,
    A_CMP,
    A_SHL,
    A_SHR,
    A_USHR,
    A_NOT,
    A_AND,
    A_OR,
    A_XOR,
    F_FUNC,
    F_JUMPP,
    F_EXIT2,
    R_BREAKPOINT,
    R_IMPDEP1;
}
