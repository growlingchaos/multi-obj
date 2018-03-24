package org.damage.multiobj;

import org.damage.multiobj.concrete.Addition;
import org.damage.multiobj.concrete.Multiplication;
import org.damage.multiobj.prx.OpHandler;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;

@Component
public class OperationEngine {

    private final OpHandler opHandler;

    private final Object proxyInstance;

    public OperationEngine(OpHandler opHandler) {
        this.opHandler = opHandler;
        this.proxyInstance = Proxy.newProxyInstance(OperationEngine.class.getClassLoader(),
                                                    new Class[]{Addition.class,
                                                                Multiplication.class},
                                                    opHandler);
    }

    public Object evaluate(String expressionString) {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(expressionString);
        StandardEvaluationContext context = new StandardEvaluationContext(proxyInstance);
        return exp.getValue(context);
    }
}
