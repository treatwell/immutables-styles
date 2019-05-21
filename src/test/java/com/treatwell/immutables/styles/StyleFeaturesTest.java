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

import java.util.Arrays;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.treatwell.immutables.styles.features.StyleFeature;

@RunWith(Parameterized.class)
public abstract class StyleFeaturesTest {

    @Parameter(0)
    public String featureName;

    @Parameter(1)
    public StyleFeatureCheck featureCheck;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Before
    public void beforeEach() {
        logger.info("Feature: {}", featureName);
    }

    @Test
    public void checkFeature() {
        featureCheck.ensureFeature(getStyleClass(), getStyleAnnotatedClass(), getGeneratedClass());
    }

    abstract Class<?> getStyleClass();

    abstract Class<?> getStyleAnnotatedClass();

    abstract Class<?> getGeneratedClass();

    protected static Set<Object[]> supportsFeatures(StyleFeature... styleFeatures) {
        return Arrays.stream(styleFeatures).map(styleFeature -> new Object[]{
            styleFeature.getHumanReadableFeatureName(),
            (StyleFeatureCheck) styleFeature::assertFeature
        }).collect(Collectors.toSet());
    }

    /**
     * This is merely the equivalent of a type alias to have cleaner definition of this class
     * Aliases a 3-parameter {@link Consumer}. Think {@link BiConsumer} but for 3 parameters, all {@link Class} instances.
     */
    private interface StyleFeatureCheck {

        void ensureFeature(Class<?> styleClass, Class<?> annotated, Class<?> generated);

    }

}
