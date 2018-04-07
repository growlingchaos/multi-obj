package org.damage.multiobj.prx;

/**
 * Tag interface to mark interfaces to load for the DSL.
 */
public interface InvokableOperation {
    /**
     * This method describes the name of the variable with witch all the other methods of the
     * extending interface should be called.
     */
    String getVarName();
}
