package net.codenest.kotcp

import kotlin.math.abs

/**
 * edX Constraint Programming: https://learning.edx.org/course/course-v1:LouvainX+Louv31x+3T2023/home
 *
 * Solve N Queens problem with a tiny constraint satisfaction problem solver.
 */
class NQueensCheckerTinyCSP(override val n: Int) : NQueensChecker {
    private val q = Array(n) { 0 }

    override fun solve(): List<Array<Int>> {
        val solutions = mutableListOf<Array<Int>>()
        dfs(0, solutions)
        return solutions.toList()
    }

    private fun dfs(idx: Int, solutions: MutableList<Array<Int>>) {
        if (idx == n) {
            if (constraintSatisfied()) {
                solutions.add(q.copyOf())
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