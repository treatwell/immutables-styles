package com.treatwell.immutables.styles;

import static com.treatwell.immutables.styles.features.SimpleStyleFeatures.DEFAULT_IMMUTABLES_NAMING_STRATEGY;
import static com.treatwell.immutables.styles.features.SimpleStyleFeatures.SERIALIZABLE_BY_JACKSON;

import java.util.Collection;

import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Modifiable;
import org.junit.runners.Parameterized.Parameters;

import com.treatwell.immutables.styles.features.BeanFriendlyModifiable;

public class JavaBeanStyleTest extends AbstractStyleFeaturesTest {

    @Parameters(name = "{0}")
    public static Collection<Object[]> expectedFeatures() {
        return supportsFeatures(
                SERIALIZABLE_BY_JACKSON,
                DEFAULT_IMMUTABLES_NAMING_STRATEGY,
                beanFriendlyModifiableClass()
        );
    }

    @Override
    Class<?> getStyleClass() {
        return JavaBeanStyle.class;
    }

    @Override
    Class<?> getStyleAnnotatedClass() {
        return MyBean.class;
    }

    @Override
    Class<?> getGeneratedClass() {
        return ImmutableMyBean.class;
    }

    @Immutable
    @Modifiable
    @JavaBeanStyle
    public interface MyBean {

        String getValue();

    }

    private static BeanFriendlyModifiable beanFriendlyModifiableClass() {
        final ModifiableMyBean modifiable = ModifiableMyBean.create();
        return new BeanFriendlyModifiable(
                modifiable::setValue,
                modifiable::getValue
        );
    }

}
