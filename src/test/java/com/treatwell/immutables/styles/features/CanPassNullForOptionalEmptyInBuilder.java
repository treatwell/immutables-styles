/*
 * Copyright 2019 Treatwell, LLC.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.treatwell.immutables.styles.features;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CanPassNullForOptionalEmptyInBuilder implements ComplexStyleFeature {

    private final Consumer<String> builderSetter;
    private final Supplier<Optional<String>> instanceGetter;

    public CanPassNullForOptionalEmptyInBuilder(Consumer<String> builderSetter, Supplier<Optional<String>> instanceGetter) {
        this.builderSetter = builderSetter;
        this.instanceGetter = instanceGetter;
    }

    @Override
    public String getHumanReadableFeatureName() {
        return "Can pass null as value for Optional field and treat it as Optional#empty()";
    }

    @Override
    public void additionalCheckWithImplementation() {
        builderSetter.accept(null);
        assertThat(instanceGetter.get()).withFailMessage(
                "Giving null as value in builder should have mapped to an empty optional."
        ).isEmpty();
    }

}
