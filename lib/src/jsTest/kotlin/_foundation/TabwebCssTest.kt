package com.github.jangalinski.tabweb._foundation

import com.github.jangalinski.tabweb._foundation.css.cssClass
import assertk.assertAll
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import kotlin.test.Test
import kotlin.test.assertFailsWith

class TabwebCssTest {

  @Test
  fun `cssClass rejects multiple class names`() {
    val invalidClassNames = listOf(
      "row row-cards",
      "col-md-6 col-lg-3",
      "btn btn-primary",
      "row\trow-cards",
      "row\nrow-cards",
    )

    assertAll {
      for (name in invalidClassNames) {
        assertFailsWith<IllegalArgumentException>("Expected '$name' with spaces to be rejected by cssClass") {
          cssClass(name)
        }
      }
    }
  }

  @Test
  fun `cssClass accepts single class names`() {
    val validNames = listOf("row", "row-cards", "col-md-6", "btn", "badge-pill")
    assertAll {
      for (name in validNames) {
        val modifier = cssClass(name)
        assertThat((modifier as TabwebValue<*>).get()).isEqualTo(name)
      }
    }
  }

  @Test
  fun `key normalized by sorting and filtering`() {
    val key = TabwebCss.key("foo bar baz foo")
    assertThat(key).isEqualTo("bar baz foo")
  }

  @Test
  fun `fill cache by querying the key`() {
    assertThat(TabwebCss.cache.keys).isEmpty()

    TabwebCss["foo bar baz foo"]

    assertThat(TabwebCss.cache.keys).contains("bar baz foo")
  }

  @Test
  fun `normalize key reduces to empty string`() {
    val key = TabwebCss.key("   ")
    assertThat(key).isEmpty()

    TabwebCss["   "]
  }

  @Test
  fun `ClassNameModifier accepts valid css class names`() {
    val validNames = listOf(
      "row",
      "row-deck",
      "col-12",
      "g-3",
      "card-10-col",
      "row row-cards",
      "row row-deck col-md-6",
      "btn btn-primary"
    )

    assertAll {
      for (name in validNames) {
        val modifier = TabwebCss.ClassNameModifier(name)
        assertThat(modifier.name).isEqualTo(name)
        assertThat(modifier.get()).isEqualTo(name)
      }
    }
  }

  @Test
  fun `ClassNameModifier rejects invalid css class names`() {
    val invalidNames = listOf(
      "",
      " ",
      "   ",
      " row",
      "row ",
      "row  row-deck",
      "row_deck",
      "row.deck",
      "row/deck",
      "row,deck",
      "row<deck>",
      "row#deck",
      "row!deck"
    )

    assertAll {
      for (name in invalidNames) {
        assertFailsWith<IllegalArgumentException>("Expected '$name' to be rejected") {
          TabwebCss.ClassNameModifier(name)
        }
      }
    }
  }

  @Test
  fun `ClassNameModifier plus operator combines class names`() {
    val modifier1 = TabwebCss.ClassNameModifier("row a")
    val modifier2 = TabwebCss.ClassNameModifier("row-deck a")
    val combinedModifier = modifier1 + modifier2

    assertThat(combinedModifier.name).isEqualTo("a row row-deck")
  }
}
