package com.github.jangalinski.tabweb.detekt

import dev.detekt.api.Config
import dev.detekt.api.Entity
import dev.detekt.api.Finding
import dev.detekt.api.Rule
import org.jetbrains.kotlin.psi.KtImportDirective

/**
 * Keeps Compose Web DOM and W3C DOM imports inside the library's internal DOM adapter.
 *
 * Component packages render through `_foundation.compose` so that direct DOM interaction,
 * attribute conversion, and Kobweb modifier handling remain centralized.
 */
class FoundationComposeOnlyRule(config: Config) : Rule(
  config,
  "Import Compose Web DOM and W3C DOM APIs only from _foundation.compose.",
) {

  override fun visitImportDirective(importDirective: KtImportDirective) {
    super.visitImportDirective(importDirective)

    val importedName = importDirective.importPath?.fqName?.asString() ?: return
    if (
      importDirective.containingKtFile.packageFqName.asString().startsWith(ALLOWED_PACKAGE) ||
      importDirective.hasDeprecatedTopLevelDeclaration() ||
      BLOCKED_IMPORT_PREFIXES.none(importedName::startsWith)
    ) {
      return
    }

    report(Finding(Entity.from(importDirective), description))
  }

  private fun KtImportDirective.hasDeprecatedTopLevelDeclaration(): Boolean =
    containingKtFile.declarations.any { declaration ->
      declaration.annotationEntries.any { annotation -> annotation.shortName?.asString() == "Deprecated" }
    }

  private companion object {
    const val ALLOWED_PACKAGE = "com.github.jangalinski.tabweb._foundation.compose"

    val BLOCKED_IMPORT_PREFIXES = listOf(
      "org.jetbrains.compose.web.dom.",
      //"org.w3c.dom.",
    )
  }
}
