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
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import io.github.azige.whitespace.command.Command;
import io.github.azige.whitespace.command.CommandFactory;
import io.github.azige.whitespace.command.DefaultCommandFactory;

/**
 *
 * @author Azige
 */
public class WhitespaceParser{

    private final BufferedReader reader;
    private final Queue<Command> commandQueue = new LinkedList<>();
    private final StateMachine sm = new StateMachine();
    private Command currentCommand;

    public WhitespaceParser(Reader reader){
        if (reader instanceof BufferedReader){
            this.reader = (BufferedReader)reader;
        }else{
            this.reader = new BufferedReader(reader);
        }
    }

    public boolean hasNext(){
        return !commandQueue.isEmpty() || sm.parseNext();
    }

    public Command<?> next(){
        if (!hasNext()){
            throw new NoSuchElementException();
        }
        currentCommand = commandQueue.poll();
        return currentCommand;
    }

    private interface State{

        State next();
    }

    private class StateMachine{

        final CommandFactory commandFactory = new DefaultCommandFactory();
        StringBuilder commandBuffer;
        boolean eofFlag = false;

        boolean parseNext(){
            State state = this::start;
            while (state != null){
                state = state.next();
            }
            return !eofFlag;
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
                BigInteger value = readNumber();
                commandQueue.offer(commandFactory.push(value));
            }else if (c == TAB){
                c = readOne();
                if (c == SPACE){
                    final int index = readNumber().intValue();
                    commandQueue.offer(commandFactory.dup(index));
                }else if (c == LF){
                    final int index = readNumber().intValue();
                    commandQueue.offer(commandFactory.remove(index));
                }else if (c == TAB){
                    throwInvalidCommand();
                }
            }else if (c == LF){
                c = readOne();
                if (c == SPACE){
                    commandQueue.offer(commandFactory.dup());
                }else if (c == TAB){
                    commandQueue.offer(commandFactory.swap());
                }else if (c == LF){
                    commandQueue.offer(commandFactory.discard());
                }
            }
            return null;
        }

        State arithmetic(){
            char c = readOne();
            if (c == SPACE){
                c = readOne();
                if (c == SPACE){
                    commandQueue.offer(commandFactory.add());
                }else if (c == TAB){
                    commandQueue.offer(commandFactory.sub());
                }else if (c == LF){
                    commandQueue.offer(commandFactory.mul());
                }
            }else if (c == TAB){
                c = readOne();
                if (c == SPACE){
                    commandQueue.offer(commandFactory.div());
                }else if (c == TAB){
                    commandQueue.offer(commandFactory.mod());
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
                commandQueue.offer(commandFactory.store());
            }else if (c == TAB){
                commandQueue.offer(commandFactory.retrieve());
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
                    commandQueue.offer(commandFactory.mark(readLabel()));
                }else if (c == TAB){
                    final String label = readLabel();
                    commandQueue.offer(commandFactory.callSubroutine(label));
                }else if (c == LF){
                    final String label = readLabel();
                    commandQueue.offer(commandFactory.jump(label));
                }
            }else if (c == TAB){
                c = readOne();
                if (c == SPACE){
                    final String label = readLabel();
                    commandQueue.offer(commandFactory.jumpIfZero(label));
                }else if (c == TAB){
                    final String label = readLabel();
                    commandQueue.offer(commandFactory.jumpIfNegative(label));
                }else if (c == LF){
                    commandQueue.offer(commandFactory.returnFromSubroutine());
                }
            }else if (c == LF){
                c = readOne();
                if (c == LF){
                    commandQueue.offer(commandFactory.exit());
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
                    commandQueue.offer(commandFactory.printChar());
                }else if (c == TAB){
                    commandQueue.offer(commandFactory.printNumber());
                }else if (c == LF){
                    throwInvalidCommand();
                }
            }else if (c == TAB){
                c = readOne();
                if (c == SPACE){
                    commandQueue.offer(commandFactory.readChar());
                }else if (c == TAB){
                    commandQueue.offer(commandFactory.readNumber());
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
                    sb.append(c == SPACE ? '0' : '1');
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
