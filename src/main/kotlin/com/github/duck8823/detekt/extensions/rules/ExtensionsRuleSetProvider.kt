package com.github.duck8823.detekt.extensions.rules

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class ExtensionsRuleSetProvider(
        override val ruleSetId: String = "extensions"
) : RuleSetProvider {
    override fun instance(config: Config): RuleSet = RuleSet(
            id = this.ruleSetId,
            rules = listOf(
                    RequireBracesIfStatements(config)
            )
    )
}
