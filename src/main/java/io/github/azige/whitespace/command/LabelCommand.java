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
 * 表示非可执行的，仅起标签作用的指令。
 *
 * @author Azige
 */
public interface LabelCommand extends Command{

    /**
     * 获得此指令所标记的标签
     *
     * @return 此指令所标记的标签
     */
    String getLabel();
}
