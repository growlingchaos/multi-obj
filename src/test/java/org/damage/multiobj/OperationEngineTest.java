package org.damage.multiobj;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class OperationEngineTest {

    @Autowired
    private OperationEngine operationEngine;

    @Test
    public void evaluate() {
        Object actual = operationEngine.evaluate("#ops.add(8,9)+#ops.multiply(5,5)");
        Assertions.assertThat(actual).isEqualTo(42);
    }

}

