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

import java.math.BigInteger;

import io.github.azige.whitespace.vm.WhitespaceVM;

/**
 *
 * @author Azige
 */
class DefaultCommandFactory extends CommandFactory{

    @Override
    public Command<BigInteger> push(BigInteger number){
        return new AbstractCommand<BigInteger>(CommandType.S_PUSH, number){
            @Override
            public void execute(WhitespaceVM vm){
                vm.getStackManipulation().push(getParameter());
            }
        };
    }

    @Override
    public Command<?> discard(){
        return new AbstractCommand<Object>(CommandType.S_DISCARD){
            @Override
            public void execute(WhitespaceVM vm){
                vm.getStackManipulation().discard();
            }
        };
    }

    @Override
    public Command<?> dup(){
        return new AbstractCommand<Object>(CommandType.S_DUP){
            @Override
            public void execute(WhitespaceVM vm){
                vm.getStackManipulation().dup();
            }
        };
    }

    @Override
    public Command<Integer> dup(int index){
        return new AbstractCommand<Integer>(CommandType.S_DUP2, index){
            @Override
            public void execute(WhitespaceVM vm){
                vm.getStackManipulation().dup(index);
            }
        };
    }

    @Override
    public Command<Integer> remove(int index){
        return new AbstractCommand<Integer>(CommandType.S_REMOVE, index){
            @Override
            public void execute(WhitespaceVM vm){
                vm.getStackManipulation().remove(index);
            }
        };
    }

    @Override
    public Command<?> swap(){
        return new AbstractCommand<Object>(CommandType.S_SWAP){
            @Override
            public void execute(WhitespaceVM vm){
                vm.getStackManipulation().swap();
            }
        };
    }

    @Override
    public Command<?> add(){
        return new AbstractCommand<Object>(CommandType.A_ADD){
            @Override
            public void execute(WhitespaceVM vm){
                vm.getArithmetic().add();
            }
        };
    }

    @Override
    public Command<?> sub(){
        return new AbstractCommand<Object>(CommandType.A_SUB){
            @Override
            public void execute(WhitespaceVM vm){
                vm.getArithmetic().sub();
            }
        };
    }

    @Override
    public Command<?> mul(){
        return new AbstractCommand<Object>(CommandType.A_MUL){
            @Override
            public void execute(WhitespaceVM vm){
                vm.getArithmetic().mul();
            }
        };
    }

    @Override
    public Command<?> div(){
        return new AbstractCommand<Object>(CommandType.A_DIV){
            @Override
            public void execute(WhitespaceVM vm){
                vm.getArithmetic().div();
            }
        };
    }

    @Override
    public Command<?> mod(){
        return new AbstractCommand<Object>(CommandType.A_MOD){
            @Override
            public void execute(WhitespaceVM vm){
                vm.getArithmetic().mod();
            }
        };
    }

    @Override
    public Command<?> store(){
        return new AbstractCommand<Object>(CommandType.H_STORE){
            @Override
            public void execute(WhitespaceVM vm){
                vm.getHeapAccess().store();
            }
        };
    }

    @Override
    public Command<?> retrieve(){
        return new AbstractCommand<Object>(CommandType.H_RETRIEVE){
            @Override
            public void execute(WhitespaceVM vm){
                vm.getHeapAccess().retrieve();
            }
        };
    }

    @Override
    public Command<String> mark(String label){
        return new AbstractCommand<String>(CommandType.F_MARK, label){
            @Override
            public void execute(WhitespaceVM vm){
                //                        vm.getFlowControl().mark(getParameter());
                throw new UnsupportedOperationException("不可执行的指令。");
            }
        };
    }

    @Override
    public Command<String> callSubroutine(String label){
        return new AbstractCommand<String>(CommandType.F_CALL, label){
            @Override
            public void execute(WhitespaceVM vm){
                vm.getFlowControl().callSubroutine(getParameter());
            }
        };
    }

    @Override
    public Command<String> jump(String label){
        return new AbstractCommand<String>(CommandType.F_JUMP, label){
            @Override
            public void execute(WhitespaceVM vm){
                vm.getFlowControl().jump(getParameter());
            }
        };
    }

    @Override
    public Command<String> jumpIfZero(String label){
        return new AbstractCommand<String>(CommandType.F_JUMPZ, label){
            @Override
            public void execute(WhitespaceVM vm){
                vm.getFlowControl().jumpIfZero(getParameter());
            }
        };
    }

    @Override
    public Command<String> jumpIfNegative(String label){
        return new AbstractCommand<String>(CommandType.F_JUMPN, label){
            @Override
            public void execute(WhitespaceVM vm){
                vm.getFlowControl().jumpIfNegative(getParameter());
            }
        };
    }

    @Override
    public Command<?> returnFromSubroutine(){
        return new AbstractCommand<String>(CommandType.F_RETURN){
            @Override
            public void execute(WhitespaceVM vm){
                vm.getFlowControl().returnFromSubroutine();
            }
        };
    }

    @Override
    public Command<?> exit(){
        return new AbstractCommand<String>(CommandType.F_EXIT){
            @Override
            public void execute(WhitespaceVM vm){
                vm.getFlowControl().exit();
            }
        };
    }

    @Override
    public Command<?> printChar(){
        return new AbstractCommand<String>(CommandType.I_PCHAR){
            @Override
            public void execute(WhitespaceVM vm){
                vm.getIO().printChar();
            }
        };
    }

    @Override
    public Command<?> printNumber(){
        return new AbstractCommand<String>(CommandType.I_PNUM){
            @Override
            public void execute(WhitespaceVM vm){
                vm.getIO().printNumber();
            }
        };
    }

    @Override
    public Command<?> readChar(){
        return new AbstractCommand<String>(CommandType.I_RCHAR){
            @Override
            public void execute(WhitespaceVM vm){
                vm.getIO().readChar();
            }
        };
    }

    @Override
    public Command<?> readNumber(){
        return new AbstractCommand<String>(CommandType.I_RNUM){
            @Override
            public void execute(WhitespaceVM vm){
                vm.getIO().readNumber();
            }
        };
    }

}
