package com.treatwell.immutables.styles;

import static com.treatwell.immutables.styles.constants.ClassNamePatterns.PREFIX_IMMUTABLE;
import static com.treatwell.immutables.styles.constants.AccessorNamePatterns.PREFIX_GET;
import static com.treatwell.immutables.styles.constants.AccessorNamePatterns.PREFIX_IS;

import org.immutables.value.Value.Style;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * This defines the default style that we use for all our Immutable classes within the
 * Treatwell code. In particular, this allows for immutable objects to be used as the actual
 * DTO objects for a given service API. For example, we can define an API of:
 *
 * <pre>{@code
 *  public interface VenueService {
 *      Venue findById(VenueId id);
 *  }
 *
 *  @Immutable
 *  @JsonSerialize(as = ImmutableVenue.class)
 *  @JsonDeserialize(as = ImmutableVenue.class)
 *  public interface Venue extends IdentifiedBy<VenueId> {
 *      VenueId getVenueId();
 *      String getName();
 *      List<SomethingElse> getOtherProperty();
 *  }
 * }</pre>
 * <p>
 * <p>
 * where the service implementation loads some data, and then converts it to an {@code ImmutableVenue}
 * either because the domain-class implements the Venue interface (e.g. {@code return ImmutableVenue.copyOf(domainVenue);}
 * or by using the explicit builder:
 *
 * <pre>{@code
 * return ImmutableVenue.builder()
 *     .id(domainId)
 *     .name(domainName)
 *     .otherProperty(emptyList())
 *     .build();
 * }</pre>
 * <p>
 * This annotation can be applied on within a {@code package-info.java} file to apply this style by default
 * to all code within the given package.
 */
@Style(
        // A. Properties defining how clients use the Immutables

        // Allow for simple boolean types as well.
        get = {PREFIX_IS, PREFIX_GET},
        // Take the type name (`Xyz`) and prefix the implementation (`ImmutableXyz`)
        typeImmutable = PREFIX_IMMUTABLE,

        // B. Internal implementation details

        // Optional<Xyz> expressly indicates that empty values are possible, so we should clearly allow
        // for null values to be provided to the unwrapped builder.xyz/withXyz methods.
        optionalAcceptNullable = true,
        // Require for Hibernate and some other libraries to be able to construct instances internally.
        privateNoargConstructor = true,
        // We allow some specific annotations to be passed through when provided on the abstract
        // class/interface, as they may be required on the underlying single public final implementation
        passAnnotations = {JsonTypeName.class, JsonPropertyOrder.class, JsonProperty.class},
        // Multiple calls to builder methods usually indicate copy/paste issues or possibly bugs,
        // so we enforce the use of strict builders for our immutable objects.
        strictBuilder = true,
        // We will let Jackson work its normal magic to compute property names, which also provides us the flexibility
        // to go and override them more easily when we want to.
        forceJacksonPropertyNames = false
)
@JsonSerialize // Triggers Jackson integration on all users.
public @interface DefaultStyle {

}
