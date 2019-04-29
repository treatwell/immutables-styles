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

package com.treatwell.immutables.styles.features;

public final class SimpleStyleFeatures {

    public static final StyleFeature DEFAULT_IMMUTABLES_NAMING_STRATEGY = new DefaultImmutablesNamingStrategy();
    public static final StyleFeature ABSTRACT_STRIPPING_NAMING_STRATEGY = new AbstractStrippingNamingStrategy();
    public static final StyleFeature APPEND_EVENT_NAMING_STRATEGY = new AppendEventNamingStrategy();

    public static final StyleFeature RECOGNIZES_BOOLEAN_GETTERS = new RecognizesBooleanGetters();
    public static final StyleFeature GENERATED_CLASS_IS_PUBLIC = new GeneratedClassHasPublicVisibility();
    public static final StyleFeature HAS_PRIVATE_NO_ARG_CONSTRUCTOR = new PrivateNoArgConstructor();
    public static final StyleFeature SERIALIZABLE_BY_JACKSON = new SerializableByJackson();

    private SimpleStyleFeatures() {
    }

}
