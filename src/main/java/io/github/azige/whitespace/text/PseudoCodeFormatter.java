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

import io.github.azige.whitespace.command.Command;
import io.github.azige.whitespace.command.LabelCommand;
import io.github.azige.whitespace.command.ParameterCommand;

/**
 * 用于将指令格式化为伪代码的类。因为伪代码并没有标准，此类生成的伪代码可能也会变化。
 *
 * @author Azige
 */
public class PseudoCodeFormatter{

    /**
     * 将指令格式化为伪代码。
     *
     * @param command 指令
     * @return 对应此指令的伪代码文本
     */
    public String format(Command command){
        if (command instanceof ParameterCommand){
            return format((ParameterCommand)command);
        }else if (command instanceof LabelCommand){
            return format((LabelCommand)command);
        }else{
            return format(command.getType());
        }
    }

    /**
     * 将带参数的指令格式化为伪代码。
     *
     * @param command 指令
     * @return 对应此指令的伪代码文本
     */
    public String format(ParameterCommand<?> command){
        Object param = command.getParam();
        if (param instanceof String){
            return format(command.getType(), (String)param);
        }
        return format(command.getType()) + " " + param;
    }

    /**
     * 将带标签指令格式化为伪代码。
     *
     * @param command 指令
     * @return 对应此指令的伪代码文本
     */
    public String format(LabelCommand command){
        return format(command.getType(), command.getLabel());
    }

    private String format(Enum<?> type){
        return type.toString();
    }

    private String format(Enum<?> type, String label){
        return format(type) + " " + label;
    }
}
