package net.codenest.kotcp.engine.search

import net.codenest.kotcp.engine.Solution
import net.codenest.kotcp.engine.Variable
import net.codenest.kotcp.engine.constraint.Constraint
import java.util.*

class DFSearch(private val variables: List<Variable>) {

    fun dfs(): List<Solution<Int>> {
        val solutions = ArrayList<Solution<Int>>()

        if (variables.any { it.domain.size() == 0 }) {
            return solutions
        }

        val notFixedVar = variables.firstOrNull { !it.domain.isFixed() }
        if (notFixedVar == null) {
            // all variables are fixed, a solution is found
            solutions.add(Solution(variables.associateWith { it.domain.min() }))
        } else {
            val valueToFix: Int = notFixedVar.domain.min()

            val backups: List<Int> = backupDomains()

            // search left branch with the fixed value
            notFixedVar.domain.fix(valueToFix)
            propagate(notFixedVar.domainChangeListeners)
            solutions.addAll(dfs())

            restoreDomains(backups)

            // search right branch with the value removed
            notFixedVar.domain.remove(valueToFix)
            if (notFixedVar.domain.size() > 0) {
                propagate(notFixedVar.domainChangeListeners)
                solutions.addAll(dfs())
            }
        }
        return solutions
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