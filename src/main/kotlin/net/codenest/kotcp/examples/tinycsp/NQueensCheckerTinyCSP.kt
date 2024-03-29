package net.codenest.kotcp.examples.tinycsp

import java.util.*

/**
 * edX Constraint Programming: https://learning.edx.org/course/course-v1:LouvainX+Louv31x+3T2023/home
 *
 * Solve N Queens problem with a tiny constraint satisfaction problem solver.
 */
class NQueensCheckerTinyCSP(override val n: Int) : NQueensChecker {

    override fun solve(): List<List<Int>> {
        val csp = TinyCSP()
        val q = Array(n) { csp.makeVariable(n) }

        for (i in 0..<n) {
            for (j in i + 1..<n) {
                csp.notEqual(q[i], q[j])      // not on the same line
                csp.notEqual(q[i], q[j], i - j)  // not on the same left diagonal
                csp.notEqual(q[i], q[j], j - i)  // not the same right diagonal
            }
        }

        val solutions = ArrayList<List<Int>>()
        csp.dfs(solutions)

        return solutions
    }
}

class TinyCSP {
    private val constraints = LinkedList<Constraint>()
    private val variables = LinkedList<Variable>()

    fun makeVariable(domSize: Int): Variable {
        val x = Variable(domSize)
        variables.add(x)
        return x
    }

    fun notEqual(x: Variable, y: Variable, offset: Int = 0) {
        constraints.add(NotEqual(x, y, offset))
        fixPoint()
    }

    private fun fixPoint() {
        var fix = false
        while (!fix) {
            fix = constraints.none { it.propagate() }
        }
    }

    fun dfs(solutions: ArrayList<List<Int>>) {
        if (variables.any {it.domain.size() == 0}) {
            return
        }

        val notFixedVar = variables.firstOrNull { !it.domain.isFixed() }
        if (notFixedVar == null) {
            // all variables are fixed, a solution is found
            solutions.add(variables.map { it.domain.min() })
        } else {
            val valueToFix = notFixedVar.domain.min()

            val backups = backupDomains()
            dfsLeft(valueToFix, notFixedVar, solutions)
            restoreDomains(backups)
            dfsRight(valueToFix, notFixedVar, solutions)
        }
    }

    private fun dfsRight(
        value: Int,
        variable: Variable,
        solutions: ArrayList<List<Int>>
    ) {
        variable.domain.remove(value)
        if (variable.domain.size() > 0) {
            fixPoint()
            dfs(solutions)
        }
    }

    private fun dfsLeft(
        value: Int,
        variable: Variable,
        solutions: ArrayList<List<Int>>
    ) {
        if (variable.domain.contain(value)) {
            variable.domain.fix(value)
            fixPoint()
            dfs(solutions)
        }
    }

    private fun backupDomains(): List<Domain> {
        return variables.map {
            v -> v.domain.clone()
        }
    }

    private fun restoreDomains(backups: List<Domain>) {
        variables.zip(backups).forEach { (variable, backup) ->
            variable.domain = backup
        }
    }
}

abstract class Constraint {

    /**
     * Propagate the constraint
     *
     * @return true if any value could be removed.
     */
    abstract fun propagate(): Boolean
}

class NotEqual(private val x: Variable, private val y: Variable, private val offset: Int = 0) : Constraint() {

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

class Variable(n: Int) {
    var domain = Domain(n)
}

class Domain(n: Int) {
    private var values = BitSet(n).apply { this.set(0, n) }

    constructor(dom: BitSet) : this(0) {
        values = dom
    }

    fun isFixed() = size() == 1

    fun min() = values.nextSetBit(0)

    fun contain(v: Int): Boolean = v in 0..<values.size() && values[v]

    fun remove(v: Int): Boolean {
        if (contain(v)) {
            values.clear(v)
            return true
        }
        return false
    }

    fun fix(v: Int): Boolean {
        if (contain(v)) {
            values.clear()
            values.set(v)
            return true
        }
        return false
    }

    fun clone(): Domain {
        return Domain(values.clone() as BitSet)
    }

    fun size() = values.cardinality()
}