package net.codenest.kotcp.engine.constraint

abstract class Constraint {

    /**
     * Propagate changes of the variable domains to all constraints
     *
     * @param onChangeConstraints accumulate all propagated constraints
     *
     * @return true if any value could be removed.
     */
    abstract fun propagate(onChangeConstraints: ArrayList<Constraint>): Boolean
}