package net.codenest.kotcp.examples

import net.codenest.kotcp.engine.Solution
import net.codenest.kotcp.engine.TinyKotCP
import net.codenest.kotcp.engine.search.DFSearch

/**
 * edX Constraint Programming: https://learning.edx.org/course/course-v1:LouvainX+Louv31x+3T2023/home
 *
 * Solve N Queens problem with a tiny constraint satisfaction problem solver.
 */
class NQueensCheckerKotCP(val n: Int) {

    fun solve(): List<Solution<Int>> {
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