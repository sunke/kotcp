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
                csp.notEqual(q[i], q[j])               // not on the same line
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
    }

    private fun propagate(constraints: List<Constraint>) {
        var noChange = false
        val onChangeConstraints = ArrayList(constraints)
        while (!noChange) {
            val onChangeConstraintsBefore = ArrayList(onChangeConstraints)
            noChange = onChangeConstraintsBefore.none { it.propagate(onChangeConstraints) }
        }
    }

    fun dfs(solutions: MutableList<List<Int>>) {
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

            // search left branch with the fixed value
            notFixedVar.domain.fix(valueToFix)
            propagate(notFixedVar.domainChangeListeners)
            dfs(solutions)

            restoreDomains(backups)

            // search right branch with the value removed
            notFixedVar.domain.remove(valueToFix)
            if (notFixedVar.domain.size() > 0) {
                propagate(notFixedVar.domainChangeListeners)
                dfs(solutions)
            }
        }
    }

    private fun backupDomains(): List<Int> {
        return variables.map { it.domain.size() }
    }

    private fun restoreDomains(backups: List<Int>) {
        variables.zip(backups).forEach { (variable, rs) ->
            variable.domain.restore(rs)
        }
    }
}

