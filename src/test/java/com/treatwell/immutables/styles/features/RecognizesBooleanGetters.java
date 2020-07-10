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

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toCollection;
import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class RecognizesBooleanGetters implements StyleFeature {

    @Override
    public String getHumanReadableFeatureName() {
        return "Recognizes \"is*\" methods as accessors/getters";
    }

    @Override
    public void assertFeature(Class<?> style, Class<?> annotated, Class<?> generated) {
        final Set<Method> expected = readBooleanGetters(annotated);
        assertThat(expected)
            .withFailMessage("Tested style has added support for is*-named getter methods, please ensure your annotated test class has at least one.")
            .isNotEmpty();

        final Set<Method> actual = readBooleanGetters(generated);

        actual.forEach(generatedGetter -> expected.removeIf(annotatedGetter -> annotatedGetter.getName().equals(generatedGetter.getName())));

        assertThat(expected).isEmpty();
    }

    private Set<Method> readBooleanGetters(Class<?> clazz) {
        return stream(clazz.getDeclaredMethods()).filter(method -> method.getName().startsWith("is")).collect(toCollection(HashSet::new));
    }

}
