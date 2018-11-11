package com.github.duck8823.detekt.extensions

import com.github.duck8823.detekt.extensions.rules.RequireBracesIfStatements
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class ExtensionsRuleSetProvider : RuleSetProvider {

    override val ruleSetId: String = "extensions"

    override fun instance(config: Config): RuleSet = RuleSet(
            id = this.ruleSetId,
            rules = listOf(
                    RequireBracesIfStatements()
            )
    )
}
