package com.github.duck8823.detekt.extensions.reports


import io.gitlab.arturbosch.detekt.core.processors.logicalLinesKey
import io.gitlab.arturbosch.detekt.api.ConsoleReport
import io.gitlab.arturbosch.detekt.api.Detektion

class BuildFailureExtReport : ConsoleReport() {

    override fun render(detektion: Detektion): String? {
        val lloc = detektion.getData(logicalLinesKey)
        val smells = detektion.findings.entries.sumBy { it.value.size }
        val dirty = smells * 1000 / (lloc as Int)
        if ( dirty >= 10 ) {
            throw RuntimeException("dirty: $dirty smells per 1,000 lloc")
        }
        return null
    }
}
