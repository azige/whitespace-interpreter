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

import static io.github.azige.whitespace.Constant.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigInteger;

/**
 *
 * @author Azige
 */
public class Interpreter{

    private final WhitespaceVM vm;

    public Interpreter(){
        this(new WhitespaceVMImpl());
    }

    public Interpreter(WhitespaceVM vm){
        this.vm = vm;
    }

    public void interpret(String script){
        interpret(new StringReader(script));
    }

    public void interpret(Reader input){
        BufferedReader reader;
        if (input instanceof BufferedReader){
            reader = (BufferedReader)input;
        }else{
            reader = new BufferedReader(input);
        }
        new StateMachine(reader).parse();
    }

    public void run(){
        while (vm.getFlowControl().nextCommand()){
        }
    }

    private interface State{

        State next();
    }

    private class StateMachine{

        BufferedReader reader;
        FlowControl flow = vm.getFlowControl();
        StringBuilder commandBuffer;
        boolean eofFlag = false;

        public StateMachine(BufferedReader reader){
            this.reader = reader;
        }

        void parse(){
            while (!eofFlag){
                State state = this::start;
                while (state != null){
                    state = state.next();
                }
            }
        }

        State start(){
            commandBuffer = new StringBuilder();
            char c = readOne();
            if (c == SPACE){
                return this::stack;
            }else if (c == TAB){
                c = readOne();
                if (c == SPACE){
                    return this::arithmetic;
                }else if (c == TAB){
                    return this::heap;
                }else if (c == LF){
                    return this::io;
                }
            }else if (c == LF){
                return this::flow;
            }
            return null;
        }

        State stack(){
            char c = readOne();
            if (c == SPACE){
                final BigInteger value = readNumber();
                flow.addCommand(() -> vm.getStackManipulation().push(value));
            }else if (c == TAB){
                c = readOne();
                if (c == SPACE){
                    final int index = readNumber().intValue();
                    flow.addCommand(() -> vm.getStackManipulation().dup(index));
                }else if (c == LF){
                    final int index = readNumber().intValue();
                    flow.addCommand(() -> vm.getStackManipulation().remove(index));
                }else if (c == TAB){
                    throwInvalidCommand();
                }
            }else if (c == LF){
                c = readOne();
                if (c == SPACE){
                    flow.addCommand(() -> vm.getStackManipulation().dup());
                }else if (c == TAB){
                    flow.addCommand(() -> vm.getStackManipulation().swap());
                }else if (c == LF){
                    flow.addCommand(() -> vm.getStackManipulation().discard());
                }
            }
            return null;
        }

        State arithmetic(){
            char c = readOne();
            if (c == SPACE){
                c = readOne();
                if (c == SPACE){
                    flow.addCommand(() -> vm.getArithmetic().add());
                }else if (c == TAB){
                    flow.addCommand(() -> vm.getArithmetic().sub());
                }else if (c == LF){
                    flow.addCommand(() -> vm.getArithmetic().mul());
                }
            }else if (c == TAB){
                c = readOne();
                if (c == SPACE){
                    flow.addCommand(() -> vm.getArithmetic().div());
                }else if (c == TAB){
                    flow.addCommand(() -> vm.getArithmetic().mod());
                }else if (c == LF){
                    throwInvalidCommand();
                }
            }else if (c == LF){
                throwInvalidCommand();
            }
            return null;
        }

        State heap(){
            char c = readOne();
            if (c == SPACE){
                flow.addCommand(() -> vm.getHeapAccess().store());
            }else if (c == TAB){
                flow.addCommand(() -> vm.getHeapAccess().retrieve());
            }else if (c == LF){
                throwInvalidCommand();
            }
            return null;
        }

        State flow(){
            char c = readOne();
            if (c == SPACE){
                c = readOne();
                if (c == SPACE){
                    flow.mark(readLabel());
                }else if (c == TAB){
                    final String label = readLabel();
                    flow.addCommand(() -> vm.getFlowControl().callSubroutine(label));
                }else if (c == LF){
                    final String label = readLabel();
                    flow.addCommand(() -> vm.getFlowControl().jump(label));
                }
            }else if (c == TAB){
                c = readOne();
                if (c == SPACE){
                    final String label = readLabel();
                    flow.addCommand(() -> vm.getFlowControl().jumpIfZero(label));
                }else if (c == TAB){
                    final String label = readLabel();
                    flow.addCommand(() -> vm.getFlowControl().jumpIfNegative(label));
                }else if (c == LF){
                    flow.addCommand(() -> vm.getFlowControl().returnFromSubroutine());
                }
            }else if (c == LF){
                c = readOne();
                if (c == LF){
                    flow.addCommand(() -> vm.getFlowControl().exit());
                }else{
                    throwInvalidCommand();
                }
            }
            return null;
        }

        State io(){
            char c = readOne();
            if (c == SPACE){
                c = readOne();
                if (c == SPACE){
                    flow.addCommand(() -> vm.getIO().printChar());
                }else if (c == TAB){
                    flow.addCommand(() -> vm.getIO().printNumber());
                }else if (c == LF){
                    throwInvalidCommand();
                }
            }else if (c == TAB){
                c = readOne();
                if (c == SPACE){
                    flow.addCommand(() -> vm.getIO().readChar());
                }else if (c == TAB){
                    flow.addCommand(() -> vm.getIO().readNumber());
                }else if (c == LF){
                    throwInvalidCommand();
                }
            }else if (c == LF){
                throwInvalidCommand();
            }
            return null;
        }

        char readOne(){
            try{
                while (true){
                    int c = reader.read();
                    if (c == -1){
                        eofFlag = true;
                        return (char)c;
                    }
                    if (c == SPACE){
                        commandBuffer.append("[Space]");
                    }else if (c == TAB){
                        commandBuffer.append("[Tab]");
                    }else if (c == LF){
                        commandBuffer.append("[LF]");
                    }else{
                        continue;
                    }
                    return (char)c;
                }
            }catch (IOException ex){
                throw new WhitespaceException(ex);
            }
        }

        BigInteger readNumber(){
            boolean signFlag = true;
            boolean negative = false;
            BigInteger value = BigInteger.ZERO;
            while (true){
                char c = readOne();
                if (signFlag){
                    if (c == SPACE){
                        signFlag = false;
                    }else if (c == TAB){
                        negative = true;
                        signFlag = false;
                    }else if (c == LF){
                        throw new WhitespaceException("无效的数值");
                    }
                }else{
                    if (c == SPACE){
                        value = value.shiftLeft(1);
                    }else if (c == TAB){
                        value = value.shiftLeft(1);
                        value = value.or(BigInteger.ONE);
                    }else if (c == LF){
                        if (negative){
                            value = value.negate();
                        }
                        return value;
                    }
                }
            }
        }

        String readLabel(){
            StringBuilder sb = new StringBuilder();
            while (true){
                char c = readOne();
                if (c == SPACE || c == TAB){
                    sb.append(c);
                }else if (c == LF){
                    return sb.toString();
                }
            }
        }

        void throwInvalidCommand(){
            throw new WhitespaceException("无效的指令：" + commandBuffer);
        }
    }
}
