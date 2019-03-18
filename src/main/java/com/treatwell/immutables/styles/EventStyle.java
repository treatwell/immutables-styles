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
 * It can then be sent over the wire between services, to and from queues (RabbitMQ, etc...)
 *
 * @implNote Due to (our) events having an {@code occuredAt} field, and deserialization of it <TBD after Steve explains it to my dumb self>
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
// We do NOT want to trigger Jackson serialization on event classes or else the id + occurredAt fields
// are rebuilt to all new values during unmarshalling and then not preserved. One problem here is that
// if people will have made Event classes but unwittingly marked them with Jackson annotations which will
// then apply usual builder constraints during unmarshalling, but would reset id and occurredAt properties.
// Happily to date, this hasn't happened.
//
// Options to enable this are:
// 1. Mark the getters on DomainEvent as @Default rather than @Derived, but this allows clients to set
//    them manually in the builder, which we would rather not permit, as it's more likely to go wrong.
// 2. Wait for https://github.com/immutables/immutables/issues/690 to be fixed (or do it ourselves)
// @JsonSerialize
public @interface EventStyle {

}
