package com.treatwell.common.utils.immutables;

import static org.immutables.value.Value.Style.ImplementationVisibility.PUBLIC;

import org.immutables.value.Value.Style;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.treatwell.common.utils.identity.IdentitySequence;

/**
 * A style designed to be used to define classes that hold information about domain Events that have
 * occurred. An event class can be defined of the form:
 *
 * <code><pre>
 * @Value.Immutable
 * abstract class VenuePublished extends DomainEvent {
 *     private static final long serialVersionUID = 1L;
 *     String getName();
 * }
 * </pre></code>
 *
 * which will cause an event class of {@code VenuePublishedEvent} to be generated to hold the actual
 * event information, and be sent over the wire (and possibly via RabbitMQ etc.)
 */
@Style(
    // A. Properties defining how clients use the Immutables

    // Allow for simple boolean types as well.
    get = {"is*", "get*"}, // Allow for simple boolean types as well.
    // Use a suffix of "Event"
    typeImmutable = "*Event",
    // Event definitions may be package-private, but we certainly want the impls to be public
    visibility = PUBLIC,

    // B. Internal implementation details

    optionalAcceptNullable = true, // Optional<Xyz> expressly indicates that empty values are possible !?
    privateNoargConstructor = true, // allow hibernate etc. to instantiate immutables
    // We allow some specific annotations to be passed through when provided on the abstract
    // class/interface, as they may be required on the underlying single public final implementation
    passAnnotations = {JsonTypeName.class, JsonPropertyOrder.class, JsonProperty.class, IdentitySequence.class},
    // Multiple calls to builder methods usually indicate copy/paste issues or possibly bugs,
    // so we enforce the use of strict builders for our event objects.
    strictBuilder = true
)
public @interface EventStyle {
}
