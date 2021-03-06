package org.damage.multiobj.concrete;

import org.damage.multiobj.prx.InvokableOperation;

public interface Multiplication<T> extends InvokableOperation {

    default String getVarName() {
        return "ops";
    }

    T multiply(T first, T second);


}
