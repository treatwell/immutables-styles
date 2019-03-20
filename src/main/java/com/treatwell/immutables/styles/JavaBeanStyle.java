package com.treatwell.immutables.styles;

import static com.treatwell.immutables.styles.constants.AccessorNamePatterns.PREFIX_GET;
import static com.treatwell.immutables.styles.constants.AccessorNamePatterns.PREFIX_IS;
import static com.treatwell.immutables.styles.constants.ClassNamePatterns.PREFIX_IMMUTABLE;

import org.immutables.value.Value.Modifiable;
import org.immutables.value.Value.Style;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * This {@link Style} is used when we require Java Beans-like support for frameworks delegation (Hibernate & co for example).
 * <p>
 * Immutables' default style, when combined with their {@link Modifiable} (here always always) generates an additional subclass that can be converted to
 * and from standard immutable implementation to have support for setters. Unfortunately, the default style does not guarantee that bean-like methods will be
 * matched as they might not have standard name and {@code void} return type.
 * This style guarantees it.
 * <p>
 * For example, a class like:
 * <pre>{@code
 *   @Immutable
 *   @JavaBeanStyle
 *   interface MyBean {
 *       String getValue();
 *   }
 * }</pre>
 * <p>
 * Will generate both a standard immutable implementation usable with:
 * <pre>{@code ImmutableMyBean.builder().value("the value").build();}</pre>
 * <p>
 * And a {@code ModifiableMyBean} implementation:
 * <pre>{@code
 *  ModifiableMyBean modifiableInstance = ModifiableMyBean.create();
 *  modifiableInstance.setValue("the value"); // returns type is void here
 *
 *  ImmutableMyBean immutableInstance = modifiableInstance.toImmutable(); // can convert to immutable when done with it
 *  ModifiableMyBean backToModifiable = ModifiableMyBean.create().from(immutableInstance); // or change you mind and do more later on
 * }</pre>
 *
 * @apiNote Usage of this style should always be in combination with {@link Modifiable}.
 */
@Style(
        /*
         * API SPECIFICATION
         * - Accessors are methods with name prefixed with "get" or "is"
         * - Naming strategy is `Xyz` -> `ImmutableXyz`
         * - The @Modifiable-generated class is JavaBeans-compliant (void setters)
         */
        get = {PREFIX_IS, PREFIX_GET},
        typeImmutable = PREFIX_IMMUTABLE,
        beanFriendlyModifiables = true,

        /*
         * SERIALIZATION PROPERTIES
         * - Common Jackson annotations are passed down to the generated class
         * - Immutables is to *NOT* generate Jackson property names, and instead let Jackson infer those itself
         */
        passAnnotations = {JsonTypeName.class, JsonPropertyOrder.class, JsonProperty.class},
        forceJacksonPropertyNames = false
)
@JsonSerialize
public @interface JavaBeanStyle {

}
