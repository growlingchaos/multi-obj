package org.damage.multiobj.prx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class OpHandler implements InvocationHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Set<Operation> operations;


    public OpHandler(Set<Operation> operations) {
        this.operations = operations;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("invoked {}", method.getName());
        for (Operation operation : operations) {
            if (hasMethod(operation, method)) {
                return safeInvoke(operation, method, args);
            }
        }
        throw new RuntimeException("No method implemented for " + method.getName());
    }

    private Object safeInvoke(Operation operation, Method method, Object[] args) {
        try {
            if (ReflectionUtils.isToStringMethod(method)) {
                return toString();
            }
            return method.invoke(operation, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            ReflectionUtils.handleReflectionException(e);
        }
        return null;
    }

    private boolean hasMethod(Operation operation, Method method) {
        try {
            operation.getClass().getMethod(method.getName(), method.getParameterTypes());
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "OpHandler{" + "operations=" + operations + '}';
    }
}
