package net.codenest.kotcp.engine.constraint

import net.codenest.kotcp.engine.Variable


class NotEqual(private val x: Variable, private val y: Variable, private val offset: Int = 0) : Constraint() {

    init {
        x.domainChangeListeners.add(this)
        y.domainChangeListeners.add(this)
    }

    override fun propagate(onChangeConstraints: MutableList<Constraint>): Boolean {
        if (x.domain.isFixed()) {
            val removed = y.domain.remove(x.domain.min() - offset)
            if (removed) {
                onChangeConstraints.addAll(y.domainChangeListeners)
            }
            return removed
        }
        if (y.domain.isFixed()) {
            val removed = x.domain.remove(y.domain.min() + offset)
            if (removed) {
                onChangeConstraints.addAll(x.domainChangeListeners)
            }
            return removed
        }
        return false
    }
}