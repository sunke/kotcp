package net.codenest.kotcp.engine.constraint

private abstract class Constraint {

    /**
     * Propagate the constraint
     *
     * @return true if any value could be removed.
     */
    abstract fun propagate(): Boolean
}