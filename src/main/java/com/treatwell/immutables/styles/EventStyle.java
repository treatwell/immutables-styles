package com.treatwell.immutables.styles;

import static com.treatwell.immutables.styles.constants.ClassNamePatterns.SUFFIX_EVENT;
import static com.treatwell.immutables.styles.constants.AccessorNamePatterns.PREFIX_GET;
import static com.treatwell.immutables.styles.constants.AccessorNamePatterns.PREFIX_IS;
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
 *  @Immutable
 *  abstract class VenuePublished extends DomainEvent {
 *      private static final long serialVersionUID = 1L;
 *      String getName();
 *  }
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
 */
@Style(
        /*
         * API SPECIFICATION
         * - Accessors are methods with name prefixed with "get" or "is"
         * - Naming strategy is `Xyz` -> `XyzEvent`
         * - Generated class is always public notwithstanding the annotated one's modifiers
         */
        get = {PREFIX_IS, PREFIX_GET},
        typeImmutable = SUFFIX_EVENT,
        visibility = PUBLIC,

        /*
         * IMPLEMENTATION DETAILS
         * - Optional fields allow `null` value being set, and translate this to an empty Optional
         * - A private no-arg constructor is always generated, mostly to accomodate Hibernate and other such frameworks
         * - Builder classes generated are `strict`, i.e. they only allow setting a property once (multiple sets of the same field often is involuntary)
         */
        optionalAcceptNullable = true,
        privateNoargConstructor = true,
        strictBuilder = true,

        /*
         * SERIALIZATION PROPERTIES
         * - Common Jackson annotations are passed down to the generated class
         * - Immutables is to *NOT* generate Jackson property names, and instead let Jackson infer those itself
         */
        passAnnotations = {JsonTypeName.class, JsonPropertyOrder.class, JsonProperty.class}
)
// Please carefully look at implementation notes to make sure the current state of it suits your usage.
// JACKSON-COMPATIBLE SUBCLASS IS *NOT* GENERATED. SEE CLASS IMPLEMENTATION NOTE
public @interface EventStyle {

}
