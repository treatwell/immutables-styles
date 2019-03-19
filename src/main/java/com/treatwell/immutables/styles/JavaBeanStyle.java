package com.treatwell.immutables.styles;

import org.immutables.value.Value.Modifiable;
import org.immutables.value.Value.Style;

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
 *  @Immutable
 * @JavaBeanStyle
 *  interface MyBean {
 *      String getValue();
 *  }
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
@Style(beanFriendlyModifiables = true, forceJacksonPropertyNames = false)
@JsonSerialize
public @interface JavaBeanStyle {

}

