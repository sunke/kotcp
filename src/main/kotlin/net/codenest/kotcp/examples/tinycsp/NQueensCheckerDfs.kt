package net.codenest.kotcp.examples.tinycsp

import kotlin.math.abs

/**
 * edX Constraint Programming: https://learning.edx.org/course/course-v1:LouvainX+Louv31x+3T2023/home
 *
 * Solve N Queens problem with Depth-First-Search. It verifies constraints only when all the decisions
 * are finished.
 */
class NQueensCheckerDfs(override val n: Int) : NQueensChecker {
    private val q = Array(n) { 0 }

    override fun solve(): List<List<Int>> {
        val solutions = mutableListOf<List<Int>>()
        dfs(0, solutions)
        return solutions
    }

    private fun dfs(idx: Int, solutions: MutableList<List<Int>>) {
        if (idx == n) {
            if (constraintSatisfied()) {
                solutions.add(q.copyOf().toList())
            }
        } else {
            for (i in 0..<n) {
                q[idx] = i
                dfs(idx + 1, solutions)
            }
        }
    }

    private fun constraintSatisfied(): Boolean {
        for (i in 0..<n) {
            for (j in i + 1..<n) {
                if (q[i] == q[j] || abs(q[j] - q[i]) == j - i) return false
            }
        }
        return true
    }
}