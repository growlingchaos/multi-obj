package org.damage.multiobj;

import org.damage.multiobj.prx.OpHandler;
import org.damage.multiobj.prx.Operation;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class OperationEngine {

    private final ExpressionParser parser;

    private final StandardEvaluationContext context;

    public OperationEngine(Set<Operation> operations) {
        Map<String, Set<Operation>> operationMap = new HashMap<>();
        for (Operation op : operations) {
            Set<Operation> opSet = operationMap.computeIfAbsent(op.getName(), s -> new HashSet<>());
            opSet.add(op);
        }

        // variable -> proxy
        Map<String, Object> proxyMap = new HashMap<>();
        for (Map.Entry<String, Set<Operation>> entry : operationMap.entrySet()) {
            Object proxy = makeProxyFor(entry.getValue());
            proxyMap.put(entry.getKey(), proxy);
        }

        parser = new SpelExpressionParser();
        context = new StandardEvaluationContext();
        proxyMap.forEach(context::setVariable);
    }

    private Object makeProxyFor(Set<Operation> opSet) {
        List<Class<?>> interfaces = new ArrayList<>();
        for (Operation op : opSet) {
            interfaces.addAll(Arrays.asList(op.getClass().getInterfaces()));
        }
        return Proxy.newProxyInstance(getClass().getClassLoader(),
                                      interfaces.toArray(new Class[0]),
                                      new OpHandler(opSet));
    }

    public Object evaluate(String expressionString) {
        Expression exp = parser.parseExpression(expressionString);
        return exp.getValue(context);
    }
}
