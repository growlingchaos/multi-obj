package org.damage.multiobj.prx;

import org.damage.multiobj.concrete.Addition;
import org.damage.multiobj.concrete.Multiplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Component
public class OpHandler implements InvocationHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Addition<Integer> integerAdder;

    private final Multiplication<Integer> integerMultiplier;

    public OpHandler(Addition<Integer> integerAdder, Multiplication<Integer> integerMultiplier) {
        this.integerAdder = integerAdder;
        this.integerMultiplier = integerMultiplier;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("invoked {}", method.getName());
        if (method.getName().equals("add")) {
            return method.invoke(integerAdder, args);
        } else {
            return method.invoke(integerMultiplier, args);
        }
    }
}
