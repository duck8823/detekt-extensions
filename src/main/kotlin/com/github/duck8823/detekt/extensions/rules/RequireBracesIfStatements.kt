package com.github.duck8823.detekt.extensions.rules

import io.gitlab.arturbosch.detekt.api.*
import org.jetbrains.kotlin.psi.KtBlockExpression
import org.jetbrains.kotlin.psi.KtIfExpression

class RequireBracesIfStatements : Rule() {
    override val issue: Issue = Issue(
            id = "RequireBracesIfStatements",
            severity = Severity.Style,
            description = """\
                    |If statement was found that does not have braces. \
                    |These should be added to improve readability.""".trimMargin(),
            debt = Debt.FIVE_MINS
    )

    override fun visitIfExpression(expression: KtIfExpression) {
        if (expression.isNotBlockExpression()) {
            report(CodeSmell(
                    issue = issue,
                    entity = Entity.from(expression),
                    message = """\
                                    |If statement was found that does not have braces. \
                                    |These should be added to improve readability.""".trimMargin()
            ))
        }
        super.visitIfExpression(expression)
    }

    private fun KtIfExpression.isNotBlockExpression(): Boolean =
            this.then !is KtBlockExpression

}
