package net.codenest.kotcp

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.system.measureTimeMillis


class NQueensCheckerTest {

    @ParameterizedTest
    @CsvSource("8, 92", "9, 352")
    fun `get solutions with DFS`(n: Int, count: Int) {
        getSolutions(NQueensCheckerDfs(n), n, count)
    }

    @ParameterizedTest
    @CsvSource("10, 724", "11, 2680", "12, 14200")
    fun `get solutions with DFS + Prune`(n: Int, count: Int) {
        getSolutions(NQueensCheckerDfsPrune(n), n, count)
    }

    private fun getSolutions(checker: NQueensChecker, n: Int, count: Int) {
        val solutions: List<Array<Int>>

        val elapsed = measureTimeMillis {
            solutions = checker.solve()
        }

        assertEquals(count, solutions.size)
        println("Solve $n queens problem with execution time (ms): $elapsed")
    }

}