/*
 * Copyright 2019 Hotspring Ventures Ltd.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.treatwell.immutables.styles.features;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.function.Consumer;

public class StrictBuilder implements ComplexStyleFeature {

    private final Consumer<String> builderSetter;

    public StrictBuilder(Consumer<String> builderSetter) {
        this.builderSetter = builderSetter;
    }

    @Override
    public String getHumanReadableFeatureName() {
        return "Generates \"strict\" builders (can't set same value twice).";
    }

    @Override
    public void additionalCheckWithImplementation() {
        builderSetter.accept("first call");
        assertThatThrownBy(() -> builderSetter.accept("second call")).isInstanceOf(IllegalStateException.class);
    }

}
