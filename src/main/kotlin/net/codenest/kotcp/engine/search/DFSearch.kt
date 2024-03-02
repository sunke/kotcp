package net.codenest.kotcp.engine.search

import net.codenest.kotcp.engine.Variable
import net.codenest.kotcp.engine.constraint.Constraint
import java.util.*

class DFSearch(private val variables: LinkedList<Variable>) {

    fun dfs(solutions: MutableList<List<Int>>) {
        if (variables.any { it.domain.size() == 0 }) {
            return
        }

        val notFixedVar = variables.firstOrNull { !it.domain.isFixed() }
        if (notFixedVar == null) {
            // all variables are fixed, a solution is found
            solutions.add(variables.map { it.domain.min() })
        } else {
            val valueToFix: Int = notFixedVar.domain.min()

            val backups: List<Int> = backupDomains()

            // search left branch with the fixed value
            notFixedVar.domain.fix(valueToFix)
            propagate(notFixedVar.domainChangeListeners)
            dfs(solutions)

            restoreDomains(backups)

            // search right branch with the value removed
            notFixedVar.domain.remove(valueToFix)
            if (notFixedVar.domain.size() > 0) {
                propagate(notFixedVar.domainChangeListeners)
                dfs(solutions)
            }
        }
    }

    private fun propagate(constraints: List<Constraint>) {
        var noChange = false
        val onChangeConstraints = ArrayList(constraints)
        while (!noChange) {
            val onChangeConstraintsBefore = ArrayList(onChangeConstraints)
            noChange = onChangeConstraintsBefore.none { it.propagate(onChangeConstraints) }
        }
    }

    private fun backupDomains(): List<Int> {
        return variables.map { it.domain.size() }
    }

    private fun restoreDomains(backups: List<Int>) {
        variables.zip(backups).forEach { (variable, rs) ->
            variable.domain.restore(rs)
        }
    }
}