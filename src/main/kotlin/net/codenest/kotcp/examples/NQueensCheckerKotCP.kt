package net.codenest.kotcp.examples

import net.codenest.kotcp.engine.Variable
import net.codenest.kotcp.engine.constraint.Constraint
import net.codenest.kotcp.engine.constraint.NotEqual
import net.codenest.kotcp.engine.search.DFSearch
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

        val solutions = csp.solve(DFSearch(csp.variables))

        return solutions
    }
}

private class TinyKotCP {
    val constraints = LinkedList<Constraint>()
    val variables = LinkedList<Variable>()

    fun makeVariable(domSize: Int): Variable {
        val x = Variable(domSize)
        variables.add(x)
        return x
    }


    fun notEqual(x: Variable, y: Variable, offset: Int = 0) {
        constraints.add(NotEqual(x, y, offset))
    }

    fun solve(search: DFSearch): List<List<Int>> {
        val solutions = ArrayList<List<Int>>()
        search.dfs(solutions)
        return solutions
    }
}

