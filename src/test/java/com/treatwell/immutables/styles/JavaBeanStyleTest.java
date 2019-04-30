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

import static com.treatwell.immutables.styles.features.SimpleStyleFeatures.DEFAULT_IMMUTABLES_NAMING_STRATEGY;
import static com.treatwell.immutables.styles.features.SimpleStyleFeatures.RECOGNIZES_BOOLEAN_GETTERS;
import static com.treatwell.immutables.styles.features.SimpleStyleFeatures.SERIALIZABLE_BY_JACKSON;

import java.util.Collection;

import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Modifiable;
import org.junit.runners.Parameterized.Parameters;

import com.treatwell.immutables.styles.features.BeanFriendlyModifiable;

public class JavaBeanStyleTest extends AbstractStyleFeaturesTest {

    @Parameters(name = "{0}")
    public static Collection<Object[]> expectedFeatures() {
        return supportsFeatures(
                SERIALIZABLE_BY_JACKSON,
                RECOGNIZES_BOOLEAN_GETTERS,
                DEFAULT_IMMUTABLES_NAMING_STRATEGY,
                beanFriendlyModifiableClass()
        );
    }

    @Override
    Class<?> getStyleClass() {
        return JavaBeanStyle.class;
    }

    @Override
    Class<?> getStyleAnnotatedClass() {
        return MyBean.class;
    }

    @Override
    Class<?> getGeneratedClass() {
        return ImmutableMyBean.class;
    }

    private static BeanFriendlyModifiable beanFriendlyModifiableClass() {
        final ModifiableMyBean modifiable = ModifiableMyBean.create();
        return new BeanFriendlyModifiable(
                modifiable::setValue,
                modifiable::getValue
        );
    }

    @Immutable
    @Modifiable
    @JavaBeanStyle
    public interface MyBean {

        String getValue();

        boolean isFlagged();

    }

}
