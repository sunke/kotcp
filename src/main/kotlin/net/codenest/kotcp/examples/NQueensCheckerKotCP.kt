package net.codenest.kotcp.examples

import net.codenest.kotcp.engine.Variable
import net.codenest.kotcp.engine.constraint.Constraint
import net.codenest.kotcp.engine.constraint.NotEqual
import net.codenest.kotcp.examples.tinycsp.NQueensChecker
import java.util.*

/**
 * edX Constraint Programming: https://learning.edx.org/course/course-v1:LouvainX+Louv31x+3T2023/home
 *
 * Solve N Queens problem with a tiny constraint satisfaction problem solver.
 */
class NQueensCheckerKotCP(override val n: Int) : NQueensChecker {

    override fun solve(): List<List<Int>> {
        val csp = TinyKotCP()
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

private class TinyKotCP {
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
        if (variables.any { it.domain.size() == 0 }) {
            return
        }

        val notFixedVar = variables.firstOrNull { !it.domain.isFixed() }
        if (notFixedVar == null) {
            // all variables are fixed, a solution is found
            solutions.add(variables.map { it.domain.min() })
        } else {
            val valueToFix = notFixedVar.domain.min()

            val backups = backupDomains()

            notFixedVar.domain.fix(valueToFix)
            fixPoint()
            dfs(solutions)

            restoreDomains(backups)

            notFixedVar.domain.remove(valueToFix)
            if (notFixedVar.domain.size() > 0) {
                fixPoint()
                dfs(solutions)
            }
        }
    }

    private fun backupDomains(): List<Int> {
        return variables.map {
            v -> v.domain.size()
        }
    }

    private fun restoreDomains(backups: List<Int>) {
        variables.zip(backups).forEach { (variable, rs) ->
            variable.domain.restore(rs)
        }
    }
}

