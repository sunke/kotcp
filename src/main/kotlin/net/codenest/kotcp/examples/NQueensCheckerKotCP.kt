package net.codenest.kotcp.examples

import net.codenest.kotcp.engine.Solution
import net.codenest.kotcp.engine.Solver
import net.codenest.kotcp.engine.constraint.NotEqual
import net.codenest.kotcp.engine.search.DFSearch

/**
 *
 * Solve N Queens problem with a tiny constraint satisfaction problem solver.
 */
class NQueensCheckerKotCP(private val n: Int) {

    fun solve(): List<Solution<Int>> {
        val solver = Solver()
        val q = Array(n) { solver.makeVariable(n) }

        for (i in 0..<n) {
            for (j in i + 1..<n) {
                solver.post(NotEqual(q[i], q[j], 0))       // not on the same line
                solver.post(NotEqual(q[i], q[j], i - j))   // not on the same left diagonal
                solver.post(NotEqual(q[i], q[j], j - i))   // not on the same right diagonal
            }
        }

        val solutions = solver.solve(DFSearch(solver.variables))

        return solutions
    }
}