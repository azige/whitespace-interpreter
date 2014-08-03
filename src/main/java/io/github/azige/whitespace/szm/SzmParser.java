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

import static io.github.azige.whitespace.szm.SzmToken.Type.*;

import java.io.IOException;
import java.io.Reader;
import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import io.github.azige.whitespace.command.Command;
import io.github.azige.whitespace.text.Parser;

/**
 * 水沝淼语法解析器。
 *
 * @author Azige
 */
public class SzmParser implements Parser{

    static final String MAIN_FUNCTION_NAME = "main";
    static final String LOCAL_LABEL_SEPARATOR = "#";
    private final SzmTokenizer tokenizer;
    private final SzmCommandFactory cf;
    private final Map<SzmToken.Type, Supplier<Command>> simpleCommandMap;
    private final Map<SzmToken.Type, Function<Integer, Command>> intParamCommandMap;
    private final Map<SzmToken.Type, Function<String, Command>> stringParamCommandMap;
    private final Deque<Command> outputBuffer = new ArrayDeque<>();
    private FunctionContext functionContext = new FunctionContext(MAIN_FUNCTION_NAME);
    private boolean reachEnd = false;

    private static class FunctionContext{

        final String name;
        Map<Integer, Integer> localVariableMap = new HashMap<>();
        int count = 0;

        public FunctionContext(String name){
            this.name = name;
        }

        int putVariable(int id){
            if (!localVariableMap.containsKey(id)){
                localVariableMap.put(id, ++count);
                return count;
            }else{
                return getVariableNumber(id);
            }
        }

        int getVariableNumber(int id){
            return localVariableMap.get(id);
        }
    }

    /**
     * 以输入源和指令工厂构造一个对象，将使用默认的词法分析器。
     *
     * @param input 输入源
     * @param cf 指令工厂
     */
    public SzmParser(Reader input, SzmCommandFactory cf){
        this(new SzmTokenizerImpl(input), cf);
    }

    /**
     * 以词法分析器和指令工厂构造一个对象。
     *
     * @param tokenizer 词法分析器
     * @param cf 指令工厂
     */
    public SzmParser(SzmTokenizer tokenizer, SzmCommandFactory cf){
        this.tokenizer = tokenizer;
        this.cf = cf;
        simpleCommandMap = buildSimpleCommandMap();
        intParamCommandMap = buildIntParamCommandMap();
        stringParamCommandMap = buildStringParamCommandMap();
    }

    private Map<SzmToken.Type, Supplier<Command>> buildSimpleCommandMap(){
        Map<SzmToken.Type, Supplier<Command>> map = new HashMap<>();

        map.put(S_DUP, cf::dup);
        map.put(S_SWAP, cf::swap);
        map.put(S_DISCARD, cf::discard);
        map.put(A_ADD, cf::add);
        map.put(A_SUB, cf::sub);
        map.put(A_MUL, cf::mul);
        map.put(A_DIV, cf::div);
        map.put(A_MOD, cf::mod);
        map.put(A_CMP, cf::cmp);
        map.put(A_NOT, cf::not);
        map.put(A_AND, cf::and);
        map.put(A_OR, cf::or);
        map.put(A_XOR, cf::xor);
        map.put(F_RETURN, cf::ret);
        map.put(I_PCHAR, cf::printChar);
        map.put(I_PNUM, cf::printNumber);
        map.put(I_RCHAR, cf::readChar);
        map.put(I_RNUM, cf::readNumber);
        map.put(R_BREAKPOINT, cf::breakpoint);
        map.put(R_IMPDEP1, cf::impdep1);

        return Collections.unmodifiableMap(map);
    }

    private static Function<Integer, Command> wrapFunc(Function<BigInteger, Command> func){
        return n -> func.apply(BigInteger.valueOf(n));
    }

    private static Function<Integer, Command> wrapFunc2(Function<String, Command> func){
        return n -> func.apply(String.valueOf(n));
    }

    private Map<SzmToken.Type, Function<Integer, Command>> buildIntParamCommandMap(){
        Map<SzmToken.Type, Function<Integer, Command>> map = new HashMap<>();

        map.put(V_LOAD, cf::loadLocal);
        map.put(V_GLOAD, cf::loadGlobal);
        map.put(V_STORE, cf::storeLocal);
        map.put(V_GSTORE, cf::storeGlobal);
        map.put(S_PUSH, wrapFunc(cf::push));
        map.put(A_SHL, cf::shl);
        map.put(A_SHR, cf::shr);
        map.put(A_USHR, cf::ushr);
        map.put(F_CALL, wrapFunc2(cf::call));
        map.put(F_EXIT, cf::exit);

        return Collections.unmodifiableMap(map);
    }

    private Map<SzmToken.Type, Function<String, Command>> buildStringParamCommandMap(){
        Map<SzmToken.Type, Function<String, Command>> map = new HashMap<>();

        map.put(F_MARK, cf::mark);
        map.put(F_JUMP, cf::jump);
        map.put(F_JUMPZ, cf::jumpIfZero);
        map.put(F_JUMPN, cf::jumpIfNegative);
        map.put(F_JUMPP, cf::jumpIfPositive);

        return Collections.unmodifiableMap(map);
    }

    /**
     * 获得下一条指令。
     *
     * @return 下一条指令，如果已到达流末尾则为null
     */
    public Command next(){
        if (outputBuffer.isEmpty()){
            doParse();
        }

        return outputBuffer.poll();
    }

    private void doParse(){
        if (reachEnd){
            return;
        }

        while (true){
            SzmToken token = tokenizer.next();
            if (token == null){
                buildFunction();
                reachEnd = true;
                return;
            }
            switch (token.getType()){
                case V_LOAD:
                case V_STORE:
                    outputBuffer.offer(intParamCommandMap.get(token.getType())
                        .apply(functionContext.putVariable(tokenizer.next().getNumber())));
                    break;
                case F_FUNC:
                    buildFunction();
                    functionContext = new FunctionContext(String.valueOf(tokenizer.next().getNumber()));
                    return;
                case F_MARK:
                case F_JUMP:
                case F_JUMPZ:
                case F_JUMPP:
                case F_JUMPN:
                    outputBuffer.offer(stringParamCommandMap.get(token.getType())
                        .apply(functionContext.name + LOCAL_LABEL_SEPARATOR + tokenizer.next().getNumber()));
                    break;
                default:
                    if (simpleCommandMap.containsKey(token.getType())){
                        outputBuffer.offer(simpleCommandMap.get(token.getType()).get());
                    }else if (intParamCommandMap.containsKey(token.getType())){
                        outputBuffer.offer(intParamCommandMap.get(token.getType()).apply(tokenizer.next().getNumber()));
                    }
            }
        }
    }

    private void buildFunction(){
        outputBuffer.push(cf.function(functionContext.name, functionContext.localVariableMap.size()));
        if (functionContext.name.equals(MAIN_FUNCTION_NAME)){
            outputBuffer.offer(cf.exit());
        }else{
            outputBuffer.offer(cf.ret());
        }
    }

    @Override
    public void close() throws IOException{
        tokenizer.close();
    }
}
