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

package com.treatwell.immutables.styles;

import static com.treatwell.immutables.styles.features.SimpleStyleFeatures.ABSTRACT_STRIPPING_NAMING_STRATEGY;
import static com.treatwell.immutables.styles.features.SimpleStyleFeatures.GENERATED_CLASS_IS_PUBLIC;
import static com.treatwell.immutables.styles.features.SimpleStyleFeatures.HAS_PRIVATE_NO_ARG_CONSTRUCTOR;
import static com.treatwell.immutables.styles.features.SimpleStyleFeatures.RECOGNIZES_BOOLEAN_GETTERS;
import static com.treatwell.immutables.styles.features.SimpleStyleFeatures.SERIALIZABLE_BY_JACKSON;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.treatwell.immutables.styles.features.CanPassNullForOptionalEmptyInBuilder;
import com.treatwell.immutables.styles.features.StrictBuilder;
import com.treatwell.immutables.styles.features.StyleFeature;

public class ValueObjectStyleTest extends StyleFeaturesTest<ValueObjectStyle, ValueObjectStyleTest.AbstractSomething, Something> {

    @Override
    protected Class<ValueObjectStyle> getStyleClass() {
        return ValueObjectStyle.class;
    }

    @Override
    protected Class<AbstractSomething> getAnnotatedClass() {
        return AbstractSomething.class;
    }

    @Override
    protected Class<Something> getGeneratedClass() {
        return Something.class;
    }

    @Override
    protected List<StyleFeature> getExpectedStyleFeatures() {
        return Arrays.asList(
            HAS_PRIVATE_NO_ARG_CONSTRUCTOR,
            RECOGNIZES_BOOLEAN_GETTERS,
            SERIALIZABLE_BY_JACKSON,
            ABSTRACT_STRIPPING_NAMING_STRATEGY,
            GENERATED_CLASS_IS_PUBLIC,
            optionalPassNullFeature(),
            strictBuilders()
        );
    }

    private static StyleFeature optionalPassNullFeature() {
        final Something.Builder builder = Something.builder().something(true);
        return new CanPassNullForOptionalEmptyInBuilder(
            builder::optionalString,
            () -> builder.build().getOptionalString()
        );
    }

    private static StyleFeature strictBuilders() {
        return new StrictBuilder(Something.builder()::optionalString);
    }

    @Immutable
    @ValueObjectStyle
    abstract static class AbstractSomething {

        abstract boolean isSomething();

        abstract Optional<String> getOptionalString();

    }

}
