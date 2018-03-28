package org.damage.multiobj;

import org.assertj.core.api.Assertions;
import org.damage.multiobj.concrete.Multiplier;
import org.damage.multiobj.prx.InvokableOperation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class InvokableOperationEngineTest {

    @Autowired
    private OperationEngine operationEngine;

    @Test
    public void evaluate() {
        Object actual = operationEngine.evaluate("#ops.add(8,9)+#ops.multiply(5,5)");
        Assertions.assertThat(actual).isEqualTo(42);
    }

    @Test
    public void name() {
        InvokableOperation operation = new Multiplier();
        Class<? extends InvokableOperation> aClass = operation.getClass();
        for (Class<?> anInterface : aClass.getInterfaces()) {
            System.out.println(anInterface.getName());
            for (Class<?> iface : anInterface.getInterfaces()) {
                System.out.println(iface.getName());
            }
        }
    }
}
