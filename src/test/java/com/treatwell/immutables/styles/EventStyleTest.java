package com.treatwell.immutables.styles;

import static com.treatwell.immutables.styles.features.SimpleStyleFeatures.APPEND_EVENT_NAMING_STRATEGY;
import static com.treatwell.immutables.styles.features.SimpleStyleFeatures.GENERATED_CLASS_IS_PUBLIC;
import static com.treatwell.immutables.styles.features.SimpleStyleFeatures.HAS_PRIVATE_NO_ARG_CONSTRUCTOR;
import static com.treatwell.immutables.styles.features.SimpleStyleFeatures.RECOGNIZES_BOOLEAN_GETTERS;

import java.util.Collection;
import java.util.Optional;

import org.immutables.value.Value.Immutable;
import org.junit.runners.Parameterized.Parameters;

import com.treatwell.immutables.styles.features.CanPassNullForOptionalEmptyInBuilder;
import com.treatwell.immutables.styles.features.StrictBuilder;
import com.treatwell.immutables.styles.features.StyleFeature;

public class EventStyleTest extends AbstractStyleFeaturesTest {

    @Parameters(name = "{0}")
    public static Collection<Object[]> expectedFeatures() {
        return supportsFeatures(
                APPEND_EVENT_NAMING_STRATEGY,
                HAS_PRIVATE_NO_ARG_CONSTRUCTOR,
                RECOGNIZES_BOOLEAN_GETTERS,
                GENERATED_CLASS_IS_PUBLIC,
                optionalPassNullFeature(),
                strictBuilders()
        );
    }

    @Override
    Class<?> getStyleClass() {
        return EventStyle.class;
    }

    @Override
    Class<?> getStyleAnnotatedClass() {
        return TestAction.class;
    }

    @Override
    Class<?> getGeneratedClass() {
        return TestActionEvent.class;
    }

    @Immutable
    @EventStyle
    public interface TestAction {

        boolean isSomeFlag();

        Optional<String> getEventOptionalString();

    }

    private static StyleFeature optionalPassNullFeature() {
        final TestActionEvent.Builder builder = TestActionEvent.builder().someFlag(true);
        return new CanPassNullForOptionalEmptyInBuilder(
                builder::eventOptionalString,
                () -> builder.build().getEventOptionalString()
        );
    }

    private static StyleFeature strictBuilders() {
        return new StrictBuilder(TestActionEvent.builder()::eventOptionalString);
    }

}
