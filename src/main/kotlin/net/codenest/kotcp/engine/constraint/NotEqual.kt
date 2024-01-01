package net.codenest.kotcp.engine.constraint

import net.codenest.kotcp.engine.Variable


class NotEqual(private val x: Variable, private val y: Variable, private val offset: Int = 0) : Constraint() {

    init {
        x.domainChangeListeners.add(this)
        y.domainChangeListeners.add(this)
    }

    override fun propagate(): Boolean {
        if (x.domain.isFixed()) {
            return y.domain.remove(x.domain.min() - offset)
        }
        if (y.domain.isFixed()) {
            return x.domain.remove(y.domain.min() + offset)
        }
        return false
    }
}