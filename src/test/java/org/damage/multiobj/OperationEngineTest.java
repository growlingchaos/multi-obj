package org.damage.multiobj;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class OperationEngineTest {

    @Autowired
    private OperationEngine operationEngine;

    @Test
    void evaluate() {
        Object actual = operationEngine.evaluate("#ops.add(8,9)+#ops.multiply(5,5)");
        Assertions.assertThat(actual).isEqualTo(42);
    }

}

