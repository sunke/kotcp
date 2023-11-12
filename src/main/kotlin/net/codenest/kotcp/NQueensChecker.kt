package net.codenest.kotcp

interface NQueensChecker {
    val n: Int
    fun solve(): List<Array<Int>>
}