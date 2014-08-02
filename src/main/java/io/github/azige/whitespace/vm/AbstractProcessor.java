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
package io.github.azige.whitespace.vm;

import io.github.azige.whitespace.WhitespaceException;
import io.github.azige.whitespace.command.Command;
import io.github.azige.whitespace.command.Program;
import io.github.azige.whitespace.command.ProgramExecutor;
import io.github.azige.whitespace.command.SimpleProgramExecutor;

/**
 * 抽象的处理器。派生类只需要实现获取{@link WhitespaceVM}的引用的方法，
 * <b>如此一来此类的实例可以先于WhitespaceVM的实例而构造。</b>
 *
 * @see #getVM()
 * @author Azige
 */
public abstract class AbstractProcessor implements Processor{

    private ProgramExecutor executor = null;

    @Override
    public void loadProgram(Program program){
        executor = new SimpleProgramExecutor(getVM(), program);
    }

    @Override
    public boolean isReady(){
        return executor != null;
    }

    @Override
    public boolean executeOne(){
        checkReady();
        return executor.executeOne();
    }

    @Override
    public Command getCurrentCommand(){
        checkReady();
        return executor.getCurrentCommand();
    }

    @Override
    public void executeAll(boolean fromStart){
        checkReady();
        executor.executeAll(fromStart);
    }

    @Override
    public int getLocation(){
        checkReady();
        return executor.getLocation();
    }

    @Override
    public Program getProgram(){
        checkReady();
        return executor.getProgram();
    }

    @Override
    public void jump(String label){
        checkReady();
        executor.jump(label);
    }

    @Override
    public void reset(){
        checkReady();
        executor.reset();
    }

    @Override
    public void end(){
        checkReady();
        executor.end();
    }

    @Override
    public void call(String label){
        checkReady();
        executor.call(label);
    }

    @Override
    public void ret(){
        checkReady();
        executor.ret();
    }

    /**
     * 获得虚拟机实例。
     *
     * @return 虚拟机实例
     */
    protected abstract WhitespaceVM getVM();

    private void checkReady(){
        if (!isReady()){
            throw new WhitespaceException("未装载程序");
        }
    }

}
