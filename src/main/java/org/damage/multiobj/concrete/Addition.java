package org.damage.multiobj.concrete;

import org.damage.multiobj.prx.InvokableOperation;

public interface Addition<T extends Number> extends InvokableOperation {

    default String getVarName() {
        return "ops";
    }

    T add(T first, T second);

}
