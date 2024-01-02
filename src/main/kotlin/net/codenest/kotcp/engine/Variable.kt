package net.codenest.kotcp.engine

import net.codenest.kotcp.engine.constraint.Constraint

class Variable(n: Int) {
    val domainChangeListeners: MutableList<Constraint> = ArrayList()

    var domain: IntDomain = SparseSetIntDomain(0, n - 1)

    override fun toString(): String {
        return "{ $domain }"
    }
}