package net.codenest.kotcp

import java.util.*

class TinyCSP() {
    private val constraints = LinkedList<Constraint>()
    private val variables = LinkedList<Variable>()

    fun makeVariable(domSize: Int): Variable {
        val x = Variable(domSize)
        variables.add(x)
        return x
    }

    fun notEqual(x: Variable, y: Variable, offset: Int) {
        constraints.add(NotEqual(x, y, offset))
    }

    fun fixPoint() {
        var fix = false
        while (!fix) {
            fix = true
            for (c in constraints) {
                fix = fix && c.propagate()
            }
        }
    }
}

abstract class Constraint() {
    /**
     *
     */
    abstract fun propagate(): Boolean
}

class NotEqual(val x: Variable, val y: Variable, val offset: Int) : Constraint() {

    constructor(x: Variable, y: Variable) : this(x, y, 0)

    override fun propagate(): Boolean {
        TODO("Not yet implemented")
    }

}

class Variable(n: Int) {
    private val domain = Domain(n)
}

class Domain(n: Int) {
    private val values = BitSet(n)

    constructor(dom: BitSet): this(0) {
        values = dom
    }

    fun isFixed() = size() == 1

    fun min() = values.nextSetBit(0)

    fun size() = values.cardinality()

    fun remove(v: Int): Boolean {
        if (v in 0..<values.size() && values[v]) {
            values.clear(v)
            if (size() == 0) {
                throw RuntimeException("Inconsistency")
            }
            return true
        }
        return false
    }

    fun fix(v: Int) {
        if (!values[v]) {
            throw RuntimeException("Inconsistency")
        }
        values.clear()
        values.set(v)
    }

    fun clone(): Domain {

    }
}