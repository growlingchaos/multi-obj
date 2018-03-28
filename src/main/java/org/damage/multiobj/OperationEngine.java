package org.damage.multiobj;

import org.damage.multiobj.prx.InvokableOperation;
import org.damage.multiobj.prx.OpHandler;
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

    public OperationEngine(Set<InvokableOperation> operations) {
        parser = new SpelExpressionParser();
        context = new StandardEvaluationContext();
        Map<String, Object> proxyMap = makeVarNameToProxyMap(operations);
        proxyMap.forEach(context::setVariable);
    }

    private Map<String, Object> makeVarNameToProxyMap(Set<InvokableOperation> operations) {
        Map<String, Set<InvokableOperation>> opsByVarName = groupOperationsByVarName(operations);
        Map<String, Object> proxyMap = new HashMap<>();
        for (Map.Entry<String, Set<InvokableOperation>> entry : opsByVarName.entrySet()) {
            Object proxy = makeProxyFor(entry.getValue());
            proxyMap.put(entry.getKey(), proxy);
        }
        return proxyMap;
    }

    private Map<String, Set<InvokableOperation>> groupOperationsByVarName(Set<InvokableOperation> operations) {
        Map<String, Set<InvokableOperation>> operationMap;
        operationMap = new HashMap<>();
        for (InvokableOperation op : operations) {
            Set<InvokableOperation> opSet = operationMap.computeIfAbsent(op.getVarName(), s -> new HashSet<>());
            opSet.add(op);
        }
        return operationMap;
    }

    private Object makeProxyFor(Set<InvokableOperation> opSet) {
        List<Class<?>> interfaces = new ArrayList<>();
        for (InvokableOperation op : opSet) {
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
