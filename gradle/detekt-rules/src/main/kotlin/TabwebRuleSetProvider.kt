package com.github.jangalinski.tabweb.detekt

import dev.detekt.api.Config
import dev.detekt.api.RuleName
import dev.detekt.api.RuleSet
import dev.detekt.api.RuleSetId
import dev.detekt.api.RuleSetProvider

/**
 * Exposes the custom Tabweb detekt rules to the detekt runtime.
 *
 * The rules are intentionally tiny and repo-specific so they can enforce Kobweb conventions
 * that the framework itself does not validate.
 */
class TabwebRuleSetProvider : RuleSetProvider {

  override val ruleSetId: RuleSetId = RuleSetId("tabweb")

  override fun instance(): RuleSet = RuleSet(
    ruleSetId,
    mapOf(
      RuleName("FoundationComposeOnlyRule") to { config: Config -> FoundationComposeOnlyRule(config) },
    ),
  )
}
