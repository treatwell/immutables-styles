package com.treatwell.immutables.styles;

import java.util.Collection;
import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class StyleConstraintsTest {

    @Parameters(name = "{0}")
    public static Collection<Object[]> getConstraints() {
        return Collections.singleton(new Object[]{"Test", "wow"});
    }

    @Parameter(0)
    public String name;

    @Parameter(1)
    public String injected;

    @Test
    public void works() {
        System.out.println(injected);
    }

}
