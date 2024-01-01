package net.codenest.kotcp.examples.tinycsp

interface NQueensChecker {
    val n: Int
    fun solve(): List<Array<Int>>
}