package net.codenest.kotcp.engine

import net.codenest.kotcp.engine.constraint.Constraint
import net.codenest.kotcp.engine.search.DFSearch
import java.util.*

class Solver(
    val constraints: MutableList<Constraint> = LinkedList<Constraint>(),
    val variables: MutableList<Variable> = LinkedList<Variable>()
) {

    fun makeVariable(domSize: Int): Variable {
        val x = Variable(domSize)
        variables.add(x)
        return x
    }

    fun post(constraint: Constraint) {
        constraints.add(constraint)
    }

    fun solve(search: DFSearch): List<Solution<Int>> {
        return search.dfs()
    }
}