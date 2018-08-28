package com.github.duck8823.detekt.extensions.reports

import io.gitlab.arturbosch.detekt.api.ConsoleReport
import io.gitlab.arturbosch.detekt.api.Detektion

class BuildFailureExtReport : ConsoleReport() {

    override fun render(detektion: Detektion): String? {
        return "Hello Report"
    }
}
