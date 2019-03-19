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
        // A. Properties defining how clients use the Immutables

        // Allow for simple boolean types as well.
        get = {PREFIX_IS, PREFIX_GET}, // Allow for simple boolean types as well.
        // Use a suffix of "Event"
        typeImmutable = SUFFIX_EVENT,
        // Event definitions may be package-private, but we certainly want the impls to be public
        visibility = PUBLIC,

        // B. Internal implementation details

        optionalAcceptNullable = true, // Optional<Xyz> expressly indicates that empty values are possible !?
        privateNoargConstructor = true, // allow hibernate etc. to instantiate immutables
        // We allow some specific annotations to be passed through when provided on the abstract
        // class/interface, as they may be required on the underlying single public final implementation
        passAnnotations = {JsonTypeName.class, JsonPropertyOrder.class, JsonProperty.class},
        // Multiple calls to builder methods usually indicate copy/paste issues or possibly bugs,
        // so we enforce the use of strict builders for our event objects.
        strictBuilder = true //,
        // DISABLED FOR THE MOMENT ... - SEE BELOW
        // We will let Jackson work its normal magic to compute property names, which also provides us the flexibility
        // to go and override them more easily when we want to.
        // forceJacksonPropertyNames = false
        )
// Unstable API
// Please carefully look at implementation notes to make sure the current state of it suits your usage.
public @interface EventStyle {

}
