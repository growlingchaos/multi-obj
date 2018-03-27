package org.damage.multiobj.concrete;

import org.damage.multiobj.prx.Operation;

public interface Multiplication<T> extends Operation {

    default String getName() {
        return "ops";
    }

    T multiply(T first, T second);


}
