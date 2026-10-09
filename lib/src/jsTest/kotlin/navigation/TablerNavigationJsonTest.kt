package com.github.jangalinski.tabweb.navigation

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.github.jangalinski.tabweb.icon.TablerIcon
import kotlin.test.Test

class TablerNavigationJsonTest {

  @Test
  fun parsesTypedNavigationWithChildrenSectionsIconsAndBadgeReferences() {
    val navigation = TablerNavigationJson.parse(
      """
      {
        "root": [
          {
            "name": "Interface",
            "description": "UI components",
            "route": "/interface",
            "icon": "TI_BOX",
            "badgeRef": "new",
            "children": [
              {
                "name": "Avatars",
                "route": "/interface/avatars"
              }
            ],
            "sections": [
              {
                "name": "Components",
                "elements": [
                  {
                    "name": "Buttons",
                    "route": "/interface/buttons",
                    "icon": "TI_CLICK"
                  }
                ]
              }
            ]
          }
        ]
      }
      """.trimIndent(),
    )

    val interfaceEntry = navigation.root.single()

    assertThat(interfaceEntry.title).isEqualTo("Interface")
    assertThat(interfaceEntry.description).isEqualTo("UI components")
    assertThat(interfaceEntry.route.get()).isEqualTo("/interface")
    assertThat(interfaceEntry.icon).isEqualTo(TablerIcon.TI_BOX)
    assertThat(interfaceEntry.badgeRef).isEqualTo("new")
    assertThat(interfaceEntry.children.single().route.get()).isEqualTo("/interface/avatars")
    assertThat(interfaceEntry.sections.single().title).isEqualTo("Components")
    assertThat(interfaceEntry.sections.single().elements.single().icon)
      .isEqualTo(TablerIcon.TI_CLICK)
  }

  @Test
  fun defaultsOptionalValuesToEmptyOrNull() {
    val entry = TablerNavigationJson.parse(
      """
      {
        "root": [
          {
            "name": "Home",
            "route": "/"
          }
        ]
      }
      """.trimIndent(),
    ).root.single()

    assertThat(entry.icon).isNull()
    assertThat(entry.badgeRef).isNull()
    assertThat(entry.children).isEqualTo(emptyList())
    assertThat(entry.sections).isEqualTo(emptyList())
  }

  @Test
  fun resolvesReferencedSectionDocuments() {
    val navigation = TablerNavigationJson.parse(
      $$"""
      {
        "root": [
          {
            "name": "KDoc",
            "description": "API documentation",
            "route": "/kdoc",
            "sections": [
              { "$include": "dokka.json" }
            ]
          }
        ]
      }
      """.trimIndent(),
    ) { reference ->
      assertThat(reference).isEqualTo("dokka.json")
      """
      {
        "sections": [
          {
            "name": "com.example",
            "elements": [
              {
                "name": "Widget",
                "route": "/kdoc/com.example/Widget"
              }
            ]
          }
        ]
      }
      """.trimIndent()
    }

    val kdoc = navigation.root.single()
    assertThat(kdoc.sections.single().title).isEqualTo("com.example")
    assertThat(kdoc.sections.single().elements.single().title).isEqualTo("Widget")
  }
}
