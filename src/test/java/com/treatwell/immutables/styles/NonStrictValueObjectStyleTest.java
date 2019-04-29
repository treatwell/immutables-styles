/*
 * Copyright 2017 - 2019 Hotspring Ventures Ltd.
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

package com.treatwell.immutables.styles;

import static com.treatwell.immutables.styles.features.SimpleStyleFeatures.ABSTRACT_STRIPPING_NAMING_STRATEGY;
import static com.treatwell.immutables.styles.features.SimpleStyleFeatures.GENERATED_CLASS_IS_PUBLIC;
import static com.treatwell.immutables.styles.features.SimpleStyleFeatures.HAS_PRIVATE_NO_ARG_CONSTRUCTOR;
import static com.treatwell.immutables.styles.features.SimpleStyleFeatures.RECOGNIZES_BOOLEAN_GETTERS;
import static com.treatwell.immutables.styles.features.SimpleStyleFeatures.SERIALIZABLE_BY_JACKSON;

import java.util.Collection;
import java.util.Optional;

import org.immutables.value.Value.Immutable;
import org.junit.runners.Parameterized;

import com.treatwell.immutables.styles.features.CanPassNullForOptionalEmptyInBuilder;
import com.treatwell.immutables.styles.features.StyleFeature;

public class NonStrictValueObjectStyleTest extends AbstractStyleFeaturesTest {

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> expectedFeatures() {
        return supportsFeatures(
                HAS_PRIVATE_NO_ARG_CONSTRUCTOR,
                RECOGNIZES_BOOLEAN_GETTERS,
                SERIALIZABLE_BY_JACKSON,
                ABSTRACT_STRIPPING_NAMING_STRATEGY,
                GENERATED_CLASS_IS_PUBLIC,
                optionalPassNullFeature()
        );
    }

    @Override
    Class<?> getStyleClass() {
        return NonStrictValueObjectStyle.class;
    }

    @Override
    Class<?> getStyleAnnotatedClass() {
        return AbstractNonStrictSomething.class;
    }

    @Override
    Class<?> getGeneratedClass() {
        return NonStrictSomething.class;
    }

    private static StyleFeature optionalPassNullFeature() {
        final NonStrictSomething.Builder builder = NonStrictSomething.builder().something(true);
        return new CanPassNullForOptionalEmptyInBuilder(
                builder::optionalString,
                () -> builder.build().getOptionalString()
        );
    }

    @Immutable
    @NonStrictValueObjectStyle
    abstract static class AbstractNonStrictSomething {

        abstract boolean isSomething();

        abstract Optional<String> getOptionalString();

    }

}
