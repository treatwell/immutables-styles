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

import static com.treatwell.immutables.styles.features.SimpleStyleFeatures.APPEND_EVENT_NAMING_STRATEGY;
import static com.treatwell.immutables.styles.features.SimpleStyleFeatures.GENERATED_CLASS_IS_PUBLIC;
import static com.treatwell.immutables.styles.features.SimpleStyleFeatures.HAS_PRIVATE_NO_ARG_CONSTRUCTOR;
import static com.treatwell.immutables.styles.features.SimpleStyleFeatures.RECOGNIZES_BOOLEAN_GETTERS;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.treatwell.immutables.styles.features.CanPassNullForOptionalEmptyInBuilder;
import com.treatwell.immutables.styles.features.StrictBuilder;
import com.treatwell.immutables.styles.features.StyleFeature;

public class EventStyleTest extends StyleFeaturesTest<EventStyle, EventStyleTest.TestAction, TestActionEvent> {

    @Override
    protected Class<EventStyle> getStyleClass() {
        return EventStyle.class;
    }

    @Override
    protected Class<TestAction> getAnnotatedClass() {
        return TestAction.class;
    }

    @Override
    protected Class<TestActionEvent> getGeneratedClass() {
        return TestActionEvent.class;
    }

    @Override
    protected List<StyleFeature> getExpectedStyleFeatures() {
        return Arrays.asList(
            APPEND_EVENT_NAMING_STRATEGY,
            HAS_PRIVATE_NO_ARG_CONSTRUCTOR,
            RECOGNIZES_BOOLEAN_GETTERS,
            GENERATED_CLASS_IS_PUBLIC,
            optionalPassNullFeature(),
            strictBuilders()
        );
    }

    private static StyleFeature optionalPassNullFeature() {
        final TestActionEvent.Builder builder = TestActionEvent.builder().someFlag(true);
        return new CanPassNullForOptionalEmptyInBuilder(
            builder::eventOptionalString,
            () -> builder.build().getEventOptionalString()
        );
    }

    private static StyleFeature strictBuilders() {
        return new StrictBuilder(TestActionEvent.builder()::eventOptionalString);
    }

    @Immutable
    @EventStyle
    public interface TestAction {

        boolean isSomeFlag();

        Optional<String> getEventOptionalString();

    }

}
