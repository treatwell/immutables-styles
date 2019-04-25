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
import static com.treatwell.immutables.styles.constants.ClassNamePatterns.SUFFIX_EVENT;
import static org.immutables.value.Value.Style.ImplementationVisibility.PUBLIC;

import org.immutables.value.Value.Style;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * A {@link Style} aimed at holding information about domain Events.
 * <p>
 * An event class can be defined as follows:
 * <pre>{@code
 *   @Immutable
 *   abstract class VenuePublished extends DomainEvent {
 *       private static final long serialVersionUID = 1L;
 *       String getName();
 *   }
 * }</pre>
 * <p>
 * which will cause generation of a {@code VenuePublishedEvent} class as actual implementation for holding event information, for which an instance can be
 * created as so:
 * {@code return VenuePublishedEvent.builder().name(domainName) .build();}
 * <p>
 * It can then be sent over the wire between services, to and from queues (RabbitMQ, etc...).
 *
 * @implNote The more observant might note {@link Style#jacksonIntegration()} being set to {@code false} here (in stark contrast with out other styles) and the
 * absence of {@link com.fasterxml.jackson.databind.annotation.JsonSerialize} as well on the style class.
 * The reason for it is that events (at least at Treatwell) have generated occurence time and id in their constructor.
 * (Un)fortunately, Jackson being a good Java citizen attempts to use the constructor rather than field reflection when possible, which in this case
 * means that it will ignore the serialized occurence time and id at deserialization and use new ones (as per the constructor's semantics).
 * Thus we have to coerce Jackson, by voluntary adding a lack of serialization information, to use field reflection at deserialization time to avoid calling the
 * constructor.
 * This is a point that we'll seek to improve in the long run and why we consider this class as "unstable" for external use until we can avoid this mechanic.
 * The upstream issue is <a href="https://github.com/immutables/immutables/issues/790">Immutables/790</a>
 */
@Style(
    /*
     * API SPECIFICATION
     * - Accessors are methods with name prefixed with "get" or "is" (for simple booleans)
     * - Naming strategy is `Xyz` -> `XyzEvent`
     * - Generated class is always public while allowing (and expecting) the definition class to stay package-private
     */
    get = {PREFIX_IS, PREFIX_GET},
    typeImmutable = SUFFIX_EVENT,
    visibility = PUBLIC,

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
     * - Common Jackson annotations are passed down to the generated class
     * - Jackson property names are left as-default to avoid automatic generation of a serializable implementation. See class javadoc note.
     */
    passAnnotations = {JsonTypeName.class, JsonPropertyOrder.class, JsonProperty.class}
    //forceJacksonPropertyNames = true
    )
// Please carefully look at implementation notes to make sure the current state of it suits your usage.
// JACKSON-COMPATIBLE SUBCLASS IS *NOT* GENERATED. SEE CLASS JAVADOC NOTE.
public @interface EventStyle {

}
