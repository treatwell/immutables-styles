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

import java.util.List;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.treatwell.immutables.styles.features.StyleFeature;

@TestInstance(Lifecycle.PER_CLASS)
public abstract class StyleFeaturesTest<STYLE, ANNOTATED, GENERATED extends ANNOTATED> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ParameterizedTest(name = "[{index}] {0}")
    @ArgumentsSource(StyleFeaturesArgumentProvider.class)
    public void checkFeature(String featureName, StyleFeature styleFeature, Class<?> style, Class<?> annotated, Class<?> generated) {
        logger.info("Feature: {}", featureName);
        styleFeature.assertFeature(style, annotated, generated);
    }

    protected abstract Class<STYLE> getStyleClass();

    protected abstract Class<ANNOTATED> getAnnotatedClass();

    protected abstract Class<GENERATED> getGeneratedClass();

    protected abstract List<StyleFeature> getExpectedStyleFeatures();

}
