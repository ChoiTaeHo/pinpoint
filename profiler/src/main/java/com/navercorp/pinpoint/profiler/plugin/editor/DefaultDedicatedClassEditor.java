/*
 * Copyright 2014 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.navercorp.pinpoint.profiler.plugin.editor;

import com.navercorp.pinpoint.bootstrap.instrument.InstrumentClass;
import com.navercorp.pinpoint.bootstrap.plugin.editor.DedicatedClassEditor;
import com.navercorp.pinpoint.exception.PinpointException;

public class DefaultDedicatedClassEditor implements DedicatedClassEditor {
    private final String targetClassName;
    private final ClassRecipe recipe;
    

    public DefaultDedicatedClassEditor(String targetClassName, ClassRecipe recipe) {
        this.targetClassName = targetClassName;
        this.recipe = recipe;
    }

    @Override
    public byte[] edit(ClassLoader classLoader, InstrumentClass target) {
        try {
            recipe.edit(classLoader, target);
            
            return target.toBytecode();
        } catch (Throwable t) {
            throw new PinpointException("Fail to edit class: " + targetClassName, t);
        }
    }

    @Override
    public String getTargetClassName() {
        return targetClassName;
    }

    @Override
    public String toString() {
        return "ClassEditor[target=" + targetClassName + ", subEditors=" + recipe + "]";
    }
}
