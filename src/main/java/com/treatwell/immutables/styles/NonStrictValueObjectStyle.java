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

package com.treatwell.immutables.styles;

import static com.treatwell.immutables.styles.constants.AccessorNamePatterns.PREFIX_GET;
import static com.treatwell.immutables.styles.constants.AccessorNamePatterns.PREFIX_IS;
import static com.treatwell.immutables.styles.constants.ClassNamePatterns.PREFIX_ABSTRACT;
import static org.immutables.value.Value.Style.ImplementationVisibility.PUBLIC;

import javax.persistence.Access;

import org.immutables.value.Value.Style;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * This style is strictly the same as {@link ValueObjectStyle} except that it does generate "non-strict" builders.
 * i.e. the builder to create instances of the generated immutable class WILL allow multiple setter calls for the same field.
 * <p>
 * e.g.:
 * <pre>{@code
 *   package-private MergeableThing merge(MergeableThing overrides) {
 *       if (overrides == null) {
 *           return MergeableThing.copyOf(this);
 *       }
 *       return MergeableThing.builder()
 *               .from(this) // will call relevant setters
 *               .from(overrides) // can call some of them again if overriden ones had values already
 *               .build();
 *   }
 * }</pre>
 *
 * @see ValueObjectStyle for the general information on semantics unrelated to builder usage restrictions
 */
@Style(
        /*
         * API SPECIFICATION
         * - Accessors are methods with name prefixed with "get" or "is"
         * - Naming strategy is `AbstractXyz` -> `Xyz`
         * - Generated class is always public
         */
        get = {PREFIX_IS, PREFIX_GET},
        typeAbstract = PREFIX_ABSTRACT,
        typeImmutable = "*",
        visibility = PUBLIC,

        /*
         * IMPLEMENTATION DETAILS
         * - Optional fields allow `null` value being set, and translate this to an empty Optional
         * - A private no-arg constructor is always generated, mostly to accomodate Hibernate and other such frameworks
         * - Builder classes generated are *NOT* `strict`, i.e. they allow setting a given property multiple times
         * - Guava collections are never used by the generated classes, only standard JDK-included ones
         */
        optionalAcceptNullable = true,
        privateNoargConstructor = true,
        strictBuilder = false,
        jdkOnly = true,

        /*
         * SERIALIZATION PROPERTIES
         * - Common Jackson annotations are passed down to the generated class
         * - Immutables is to *NOT* generate Jackson property names, and instead let Jackson infer those itself
         */
        passAnnotations = {JsonTypeName.class, JsonPropertyOrder.class, JsonProperty.class, JsonSerialize.class, Access.class},
        forceJacksonPropertyNames = false
)
@JsonSerialize // Triggers Jackson serialization support
public @interface NonStrictValueObjectStyle {

}
