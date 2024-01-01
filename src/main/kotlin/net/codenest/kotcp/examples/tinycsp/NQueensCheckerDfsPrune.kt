package net.codenest.kotcp.examples.tinycsp

import kotlin.math.abs

/**
 * edX Constraint Programming: https://learning.edx.org/course/course-v1:LouvainX+Louv31x+3T2023/home
 *
 * Solve N Queens problem with Depth-First-Search and Prune. It verifies constraints on a prefix of decisions.
 */
class NQueensCheckerDfsPrune(override val n: Int) : NQueensChecker {
    private val q = Array(n) { 0 }

    override fun solve(): List<Array<Int>> {
        val solutions = mutableListOf<Array<Int>>()
        dfs(0, solutions)
        return solutions.toList()
    }

    private fun dfs(idx: Int, solutions: MutableList<Array<Int>>) {
        if (idx == n) {
            solutions.add(q.copyOf())
        } else {
            for (i in 0..<n) {
                q[idx] = i
                if (constraintSatisfied(idx)) {
                    dfs(idx + 1, solutions)
                }
            }
        }
    }

    private fun constraintSatisfied(j: Int): Boolean {
        for (i in 0..<j) {
            if (q[i] == q[j] || abs(q[j] - q[i]) == j - i) return false
        }
        return true
    }
}