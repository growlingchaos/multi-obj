package org.damage.multiobj.concrete;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class AdderTest {

    private Adder adder;

    @Before
    public void setUp() throws Exception {
        this.adder = new Adder();
    }

    @Test
    public void doNotFailOnNullParameters() {
        Throwable throwable = catchThrowable(() -> adder.add(null, 1));

        assertThat(throwable).hasMessage("Null can not be summed");
    }
}
