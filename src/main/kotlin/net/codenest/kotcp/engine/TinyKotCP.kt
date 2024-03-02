package net.codenest.kotcp.engine

import net.codenest.kotcp.engine.constraint.Constraint
import net.codenest.kotcp.engine.constraint.NotEqual
import net.codenest.kotcp.engine.search.DFSearch
import java.util.*

class TinyKotCP {
    val constraints = LinkedList<Constraint>()
    val variables = LinkedList<Variable>()

    fun makeVariable(domSize: Int): Variable {
        val x = Variable(domSize)
        variables.add(x)
        return x
    }


    fun notEqual(x: Variable, y: Variable, offset: Int = 0) {
        constraints.add(NotEqual(x, y, offset))
    }

    fun solve(search: DFSearch): List<Solution<Int>> {
        return search.dfs()
    }
}