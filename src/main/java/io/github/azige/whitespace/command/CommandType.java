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
package io.github.azige.whitespace.command;

/**
 * 表示所有的 Whitespace 命令类型的枚举。
 *
 * @author Azige
 */
public enum CommandType{

    S_PUSH,
    S_DISCARD,
    S_DUP,
    S_DUP2,
    S_REMOVE,
    S_SWAP,
    A_ADD,
    A_SUB,
    A_MUL,
    A_DIV,
    A_MOD,
    H_STORE,
    H_RETRIEVE,
    F_MARK,
    F_CALL,
    F_JUMP,
    F_JUMPZ,
    F_JUMPN,
    F_RETURN,
    F_EXIT,
    I_PCHAR,
    I_PNUM,
    I_RCHAR,
    I_RNUM
}
