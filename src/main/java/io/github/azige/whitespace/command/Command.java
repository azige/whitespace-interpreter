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
 * 表示Whitespace指令。
 *
 * @author Azige
 */
public interface Command{

    /**
     * 获得此指令的类型
     *
     * @return 此指令的类型
     */
    CommandType getType();

    public abstract class AbstractCommand implements Command{

        private final CommandType type;

        public AbstractCommand(CommandType type){
            if (type == null){
                throw new NullPointerException();
            }
            this.type = type;
        }

        @Override
        public CommandType getType(){
            return type;
        }
    }
}
