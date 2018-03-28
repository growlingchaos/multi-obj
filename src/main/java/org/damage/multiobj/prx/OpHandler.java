package org.damage.multiobj.prx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class OpHandler implements InvocationHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Set<InvokableOperation> operations;
    private final Map<MethodSignature, InvokableOperation> signatureMap;

    public OpHandler(Set<InvokableOperation> operations) {
        this.operations = operations;
        this.signatureMap = makeSignatureMap(operations);
    }

    private Map<MethodSignature, InvokableOperation> makeSignatureMap(Set<InvokableOperation> operations) {
        HashMap<MethodSignature, InvokableOperation> map = new HashMap<>();
        for (InvokableOperation operation : operations) {
            for (Method method : operation.getClass().getMethods()) {
                map.put(new MethodSignature(method), operation);
            }
        }
        return map;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("invoked {}", method.getName());
        MethodSignature signature = new MethodSignature(method);
        if (signatureMap.containsKey(signature)) {
            return safeInvoke(signatureMap.get(signature), method, args);
        }
        throw new RuntimeException("No method implemented for " + method.getName());
    }

    private Object safeInvoke(InvokableOperation operation, Method method, Object[] args) {
        try {
            if (ReflectionUtils.isToStringMethod(method)) {
                return toString();
            }
            return method.invoke(operation, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            String message = String.format("Unable to call %s on %s", method, operation);
            throw new RuntimeException(message, e);
        }
    }

    @Override
    public String toString() {
        return "OpHandler{" + "operations=" + operations + '}';
    }

    private static class MethodSignature {
        private final String name;
        private final Class<?> parameters[];

        MethodSignature(Method method) {
            this(method.getName(), method.getParameterTypes());
        }

        MethodSignature(String name, Class<?>... parameters) {
            this.name = name;
            this.parameters = parameters;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            MethodSignature that = (MethodSignature) o;
            return Objects.equals(name, that.name) && Arrays.equals(parameters, that.parameters);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(name);
            result = 31 * result + Arrays.hashCode(parameters);
            return result;
        }
    }
}
