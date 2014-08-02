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

import java.math.BigInteger;

/**
 * 表示一个Whitespace文本记号。
 *
 * @author Azige
 */
public class Token{

    private final Type type;
    private final String text;
    private final BigInteger number;

    /**
     * 定义了记号类型的枚举。
     */
    public enum Type{

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
        I_RNUM,
        NUMBER,
        LABEL;
    }

    /**
     * 以指定的类型构造一个记号。
     *
     * @param type 记号类型
     */
    public Token(Type type){
        this(type, null, null);
    }

    /**
     * 以指定的文本构造一个标签记号，其类型一定是{@link Type#LABEL}。
     *
     * @param text 标签文本
     */
    public Token(String text){
        this(Type.LABEL, text, null);
    }

    /**
     * 以指定的数值构造一个数值记号，其类型一定是{@link Type#NUMBER}。
     *
     * @param number 数值
     */
    public Token(BigInteger number){
        this(Type.NUMBER, null, number);
    }

    private Token(Type type, String text, BigInteger number){
        this.type = type;
        this.text = text;
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
     * 获得此记号的标签文本。
     *
     * @return 此记号的标签文本，若类型不是{@link Type#LABEL}则为null
     */
    public String getText(){
        return text;
    }

    /**
     * 获得此记号的数值。
     *
     * @return 此记号的数值，若类型不是{@link Type#NUMBER}则为null
     */
    public BigInteger getNumber(){
        return number;
    }
}
