package net.codenest.kotcp.engine.constraint

import net.codenest.kotcp.engine.Variable


class NotEqual(private val x: Variable, private val y: Variable, private val offset: Int = 0) : Constraint() {

    init {
        x.domainChangeListeners.add(this)
        y.domainChangeListeners.add(this)
    }

    override fun propagate(onChangeConstraints: MutableList<Constraint>): Boolean {
        var onChange = false

        if (x.domain.isFixed()) {
            onChange = y.domain.remove(x.domain.min() - offset)
            if (onChange) {
                onChangeConstraints.addAll(y.domainChangeListeners)
            }
        } else if (y.domain.isFixed()) {
            onChange = x.domain.remove(y.domain.min() + offset)
            if (onChange) {
                onChangeConstraints.addAll(x.domainChangeListeners)
            }
        }

        return onChange
    }
}