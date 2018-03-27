package org.damage.multiobj.concrete;

import org.damage.multiobj.prx.Operation;

public interface Addition<T extends Number> extends Operation {

    default String getName() {
        return "ops";
    }

    T add(T first, T second);

}
