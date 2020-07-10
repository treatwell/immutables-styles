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

import static java.lang.reflect.Modifier.isPrivate;
import static java.util.Arrays.stream;
import static org.assertj.core.api.Assertions.assertThat;

public class PrivateNoArgConstructor implements StyleFeature {

    @Override
    public String getHumanReadableFeatureName() {
        return "Has private no-arg constructor";
    }

    @Override
    public void assertFeature(Class<?> style, Class<?> annotated, Class<?> generated) {
        final boolean annotatedHasConstructor = isNoArgPrivateConstructorFound(annotated);
        final boolean generatedHasConstructor = isNoArgPrivateConstructorFound(generated);

        assertThat(annotatedHasConstructor)
            .withFailMessage("Did not expect a private no-arg constructor in annotated class (should have been interface!): %s", annotated)
            .isFalse();

        assertThat(generatedHasConstructor)
            .withFailMessage("Did not find expected private no-arg constructor for class %s generated from %s", generated, annotated)
            .isTrue();
    }

    private boolean isNoArgPrivateConstructorFound(Class<?> generated) {
        return stream(generated.getDeclaredConstructors())
            .filter(constructor -> isPrivate(constructor.getModifiers()))
            .anyMatch(constructor -> constructor.getParameterCount() == 0);
    }

}
