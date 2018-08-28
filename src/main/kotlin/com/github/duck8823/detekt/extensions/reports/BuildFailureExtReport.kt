package com.github.duck8823.detekt.extensions.reports


import io.gitlab.arturbosch.detekt.api.ConsoleReport
import io.gitlab.arturbosch.detekt.api.Detektion
import io.gitlab.arturbosch.detekt.api.PREFIX
import io.gitlab.arturbosch.detekt.core.processors.*

class BuildFailureExtReport : ConsoleReport() {

    override fun render(detektion: Detektion): String? {
        val mcc = detektion.getData(complexityKey) as Int
        val loc = detektion.getData(linesKey) as Int
        val sloc = detektion.getData(sourceLinesKey) as Int
        val lloc = detektion.getData(logicalLinesKey) as Int
        val cloc = detektion.getData(commentLinesKey) as Int
        val findings = detektion.findings.entries

        val numberOfSmells = findings.sumBy { it.value.size }
        val smellPerThousandLines = numberOfSmells * 1000 / lloc
        val mccPerThousandLines = mcc * 1000 / lloc
        val commentSourceRatio = cloc * 100 / sloc

        return with(StringBuilder()) {
            append(("Complexity Report:".format()))
            append("$loc lines of code (loc)".format(PREFIX))
            append("$sloc source lines of code (sloc)".format(PREFIX))
            append("$lloc logical lines of code (lloc)".format(PREFIX))
            append("$cloc comment lines of code (cloc)".format(PREFIX))
            append("$mcc McCabe complexity (mcc)".format(PREFIX))
            append("$numberOfSmells number of total code smells".format(PREFIX))
            append("$commentSourceRatio % comment source ratio".format(PREFIX))
            append("$mccPerThousandLines mcc per 1000 lloc".format(PREFIX))
            append("$smellPerThousandLines code smells per 1000 lloc".format(PREFIX))
            toString()
        }
    }

    private fun Any.format(prefix: String = "", suffix: String = "\n") = "$prefix$this$suffix"
}
