package net.codenest.kotcp.examples

import net.codenest.kotcp.examples.tinycsp.NQueensChecker
import net.codenest.kotcp.examples.tinycsp.NQueensCheckerDfs
import net.codenest.kotcp.examples.tinycsp.NQueensCheckerDfsPrune
import net.codenest.kotcp.examples.tinycsp.NQueensCheckerTinyCSP
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.system.measureTimeMillis


class NQueensCheckerKotCpTest {

    @ParameterizedTest
    @CsvSource("10, 724", "11, 2680", "12, 14200")
    fun `get solutions with KotCP`(n: Int, count: Int) {
        getSolutions(NQueensCheckerKotCP(n), n, count)
    }

    @Test
    fun `get solutions for 4 queens`() {
        getSolutions(NQueensCheckerKotCP(4), 4, 2)
    }


    private fun getSolutions(checker: NQueensChecker, n: Int, count: Int) {
        val solutions: List<List<Int>>

        val elapsed = measureTimeMillis {
            solutions = checker.solve()
        }

        assertEquals(count, solutions.size)
        println("Solve $n queens problem with execution time (ms): $elapsed")
    }

}