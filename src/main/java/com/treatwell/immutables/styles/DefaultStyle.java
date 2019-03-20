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
         * - Accessors are methods with name prefixed with "get" or "is"
         * - Naming strategy is `Xyz` -> `ImmutableXyz`
         */
        get = {PREFIX_IS, PREFIX_GET},
        typeImmutable = PREFIX_IMMUTABLE,

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
        passAnnotations = {JsonTypeName.class, JsonPropertyOrder.class, JsonProperty.class},
        forceJacksonPropertyNames = false
)
@JsonSerialize
public @interface DefaultStyle {

}
