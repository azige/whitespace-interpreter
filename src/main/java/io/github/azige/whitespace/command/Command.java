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

import java.io.Serializable;

/**
 * 表示Whitespace指令，指令拥有用枚举类表示的类型。指令是可以序列化的。
 *
 * @author Azige
 */
public interface Command extends Serializable{

    /**
     * 表示指令类型的标记接口。此接口应当只被枚举类实现。
     */
    public interface Type{

    }

    /**
     * 获得此指令的类型
     *
     * @return 此指令的类型
     */
    Enum<? extends Type> getType();
}
