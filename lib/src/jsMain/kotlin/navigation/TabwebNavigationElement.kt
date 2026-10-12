package com.github.jangalinski.tabweb.navigation

import com.github.jangalinski.tabweb._foundation.Url
import com.github.jangalinski.tabweb.icon.TabwebIcon

/**
 * One navigation entry, including its dropdown children and section navigation.
 *
 * Every entry has a route so route generation never needs to invent a URL for a
 * grouping entry. A site may still render an entry as a dropdown or section
 * heading when it also has [children] or [sections].
 *
 * @param title display title.
 * @param description optional supporting text.
 * @param route route represented by this entry.
 * @param icon optional generated Tabweb icon.
 * @param badgeRef optional site-owned badge registry key.
 * @param columns number of columns used when rendering direct children.
 * @param children nested top-navigation entries.
 * @param sections left-side navigation sections belonging to this entry.
 */
data class TabwebNavigationElement(
    val title: String,
    val description: String? = null,
    val route: Url,
    val icon: TabwebIcon? = null,
    val badgeRef: String? = null,
    val columns: Int = 1,
    val children: List<TabwebNavigationElement> = emptyList(),
    val sections: List<TabwebNavigationSection> = emptyList(),
)
