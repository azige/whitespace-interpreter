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

/**
 * 表示一个水沝淼文本记号。
 *
 * @author Azige
 */
public class SzmToken{

    private final Type type;
    private final Integer number;

    /**
     * 定义了记号类型的枚举。
     */
    public enum Type{

        V_LOAD,
        V_STORE,
        V_GLOAD,
        V_GSTORE,
        S_PUSH,
        S_DISCARD,
        S_DUP,
        S_SWAP,
        A_ADD,
        A_SUB,
        A_MUL,
        A_DIV,
        A_MOD,
        A_CMP,
        A_SHL,
        A_SHR,
        A_USHR,
        A_NOT,
        A_AND,
        A_OR,
        A_XOR,
        F_FUNC,
        F_MARK,
        F_CALL,
        F_JUMP,
        F_JUMPZ,
        F_JUMPN,
        F_JUMPP,
        F_RETURN,
        F_EXIT,
        I_PCHAR,
        I_PNUM,
        I_RCHAR,
        I_RNUM,
        R_BREAKPOINT,
        R_IMPDEP1,
        NUMBER;
    }

    /**
     * 以指定的类型构造一个记号。
     *
     * @param type 记号类型
     */
    public SzmToken(Type type){
        this(type, null);
    }

    /**
     * 以指定的数值构造一个数值记号，其类型一定是{@link Type#NUMBER}。
     *
     * @param number 数值
     */
    public SzmToken(Integer number){
        this(Type.NUMBER, number);
    }

    private SzmToken(Type type, Integer number){
        this.type = type;
        this.number = number;
    }

    /**
     * 获得此记号的类型。
     *
     * @return 此记号的类型
     */
    public Type getType(){
        return type;
    }

    /**
     * 获得此记号的数值。
     *
     * @return 此记号的数值，若类型不是{@link Type#NUMBER}则为null
     */
    public Integer getNumber(){
        return number;
    }
}
