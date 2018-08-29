package com.github.duck8823.detekt.extensions.reports

import io.gitlab.arturbosch.detekt.api.*
import io.gitlab.arturbosch.detekt.core.processors.*

class BuildFailureExtReport : ConsoleReport() {

    override val priority: Int = Int.MIN_VALUE

    private var complexityThresholds: Config by SingleAssign()
    private var thresholdLoc: Int by SingleAssign()
    private var thresholdSloc: Int by SingleAssign()
    private var thresholdLloc: Int by SingleAssign()
    private var thresholdCloc: Int by SingleAssign()
    private var thresholdMcc: Int by SingleAssign()
    private var thresholdSmells: Int by SingleAssign()
    private var thresholdMccPerThousandLLOC: Int by SingleAssign()
    private var thresholdSmellsPerThousandLLOC: Int by SingleAssign()

    companion object {
        private const val COMPLEXITY_THRESHOLDS = "complexityThresholds"
        private const val LOC = "loc"
        private const val SLOC = "sloc"
        private const val LLOC = "lloc"
        private const val CLOC = "cloc"
        private const val MCC = "mcc"
        private const val SMELLS = "smells"
        private const val MCC_PER_THOUSAND_LLOC = "mccPerThousandLLOC"
        private const val SMELLS_PER_THOUSAND_LLOC = "llocPertThousandLLOC"
    }

    override fun init(config: Config) {
        complexityThresholds = config.subConfig(COMPLEXITY_THRESHOLDS)
        thresholdLoc = complexityThresholds.valueOrDefault(LOC, Int.MAX_VALUE)
        thresholdSloc = complexityThresholds.valueOrDefault(SLOC, Int.MAX_VALUE)
        thresholdLloc = complexityThresholds.valueOrDefault(LLOC, Int.MAX_VALUE)
        thresholdCloc = complexityThresholds.valueOrDefault(CLOC, Int.MAX_VALUE)
        thresholdMcc = complexityThresholds.valueOrDefault(MCC, Int.MAX_VALUE)
        thresholdSmells = complexityThresholds.valueOrDefault(SMELLS, Int.MAX_VALUE)
        thresholdMccPerThousandLLOC =
                complexityThresholds.valueOrDefault(MCC_PER_THOUSAND_LLOC, Int.MAX_VALUE)
        thresholdSmellsPerThousandLLOC =
                complexityThresholds.valueOrDefault(SMELLS_PER_THOUSAND_LLOC, Int.MAX_VALUE)
    }

    override fun render(detektion: Detektion): String? {
        val loc = detektion.getData(linesKey) as Int
        val sloc = detektion.getData(sourceLinesKey) as Int
        val lloc = detektion.getData(logicalLinesKey) as Int
        val cloc = detektion.getData(commentLinesKey) as Int
        val mcc = detektion.getData(complexityKey) as Int
        val smells = detektion.findings.entries.sumBy { it.value.size }
        val mccPerThousandLines = mcc * 1000 / lloc
        val smellsPerThousandLines = smells * 1000 / lloc

        return when {
            thresholdLoc.reached(loc) -> throw BuildFailure("Build failure threshold of " +
                    "$thresholdLoc reached with $loc line of code!")
            thresholdSloc.reached(sloc) -> throw BuildFailure("Build failure threshold of " +
                    "$thresholdSloc reached with $sloc source lines of cod!")
            thresholdLloc.reached(lloc) -> throw BuildFailure("Build failure threshold of " +
                    "$thresholdLloc reached with $lloc logical lines of code!")
            thresholdCloc.reached(cloc) -> throw BuildFailure("Build failure threshold of " +
                    "$thresholdCloc reached with $cloc comment lines of code!")
            thresholdMcc.reached(mcc) -> throw BuildFailure("Build failure threshold of " +
                    "$thresholdMcc reached with $mcc McCabe complexity!")
            thresholdSmells.reached(smells) -> throw BuildFailure("Build failure threshold of " +
                    "$thresholdSmells reached with $smells number of total code smells!")
            thresholdMccPerThousandLLOC.reached(mccPerThousandLines) ->
                throw BuildFailure("Build failure threshold of $thresholdMccPerThousandLLOC  " +
                        "reached with $mccPerThousandLines mcc per 1000 lloc!")
            thresholdSmellsPerThousandLLOC.reached(smellsPerThousandLines) ->
                throw BuildFailure("Build failure threshold of $thresholdSmellsPerThousandLLOC " +
                        "reached with $smellsPerThousandLines smells per 1000 lloc!")
            else -> null
        }
    }

    private fun Int.reached(amount: Int): Boolean = !(this == 0 && amount == 0) && this != -1 && this <= amount

    class BuildFailure(message: String) : RuntimeException(message)
}
