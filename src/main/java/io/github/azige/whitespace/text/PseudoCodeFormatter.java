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

import io.github.azige.whitespace.Constant;
import io.github.azige.whitespace.command.Command;
import io.github.azige.whitespace.command.CommandType;
import io.github.azige.whitespace.command.LabelCommand;
import io.github.azige.whitespace.command.ParameterCommand;

/**
 *
 * @author Azige
 */
public class PseudoCodeFormatter{

    public String format(Command command){
        if (command instanceof ParameterCommand){
            return format((ParameterCommand)command);
        }else if (command instanceof LabelCommand){
            return format((LabelCommand)command);
        }else{
            return format(command.getType());
        }
    }

    public String format(ParameterCommand<?> command){
        Object param = command.getParam();
        if (param instanceof String){
            return format(command.getType(), (String)param);
        }
        return format(command.getType()) + " " + param;
    }

    public String format(LabelCommand command){
        return format(command.getType(), command.getLabel());
    }

    public String format(CommandType type){
        return type.toString();
    }

    public String format(CommandType type, String label){
        return format(type) + " " + label;
    }
}
