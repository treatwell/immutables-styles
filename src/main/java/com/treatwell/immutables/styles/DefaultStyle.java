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
import static com.treatwell.immutables.styles.constants.ClassNamePatterns.PREFIX_IMMUTABLE;

import org.immutables.value.Value.Style;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * This {@link Style} defines a default interface-based style for simple immutable objects.
 * <p>
 * Applied to a class as so:
 * <pre>{@code
 *   @Immutable
 *   @JsonSerialize(as = ImmutableVenue.class)
 *   @JsonDeserialize(as = ImmutableVenue.class)
 *   public interface Venue extends IdentifiedBy<VenueId> {
 *       VenueId getVenueId();
 *       String getName();
 *       List<SomethingElse> getOtherProperty();
 *   }
 * }</pre>
 * <p>
 * Generates an {@code ImmutableVenue} class that would be usable as so:
 * <pre>{@code
 *   return ImmutableVenue.builder()
 *       .id(domainId)
 *       .name(domainName)
 *       .otherProperty(emptyList())
 *       .build();
 * }</pre>
 * <p>
 * With the added advantage of being serializable by Jackson transparently.
 */
@Style(
    /*
     * API SPECIFICATION
     * - Accessors are methods with name prefixed with "get" or "is" (for simple booleans)
     * - Naming strategy is `Xyz` -> `ImmutableXyz`
     */
    get = {PREFIX_IS, PREFIX_GET},
    typeImmutable = PREFIX_IMMUTABLE,

    /*
     * IMPLEMENTATION DETAILS
     * - Optional fields allow `null` value being set, and translates this to an empty Optional when null values are passed to the builder methods
     * - A private no-arg constructor is always generated, mostly to accomodate Hibernate and other such frameworks
     * - Builder classes generated are `strict`, i.e. they only allow setting a property once (multiple sets of the same field often is involuntary)
     */
    optionalAcceptNullable = true,
    privateNoargConstructor = true,
    strictBuilder = true,

    /*
     * SERIALIZATION PROPERTIES
     * - Common Jackson annotations are passed down to the generated class (as they may be required on the underlying final implementation class)
     * - Immutables is to *NOT* generate Jackson property names, and instead let Jackson infer those itself, while we can still override the automatic ones
     * when needed
     */
    passAnnotations = {JsonTypeName.class, JsonPropertyOrder.class, JsonProperty.class},
    forceJacksonPropertyNames = false
)
@JsonSerialize // Triggers Jackson serialization support
public @interface DefaultStyle {

}
