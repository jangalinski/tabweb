/**
 * Contains abstractions and utils from org.w3c.dom so we dont pollute the lib with dependencies.
 */
package com.github.jangalinski.tabweb._foundation

import org.w3c.dom.Document
import org.w3c.dom.Window

/**
 * Marks a side effect behavior that is managed by [com.github.jangalinski.tabweb._app.TablerSettings] and can control its own htm and session state.
 */
fun interface SideEffectBehavior {
  operator fun invoke(
    sessionItem: SessionItemAdapter,
    htmlAttribute: HtmlAttributeAdapter
  )
}

/**
 * Adapter for a [Window.sessionStorage] item to be used by [SideEffectBehavior].
 */
interface SessionItemAdapter {
  operator fun get(key: SessionKey): String?
  operator fun set(key: SessionKey, value: String?)
}

/**
 * Adapter for a [Document.documentElement] to be used by [SideEffectBehavior].
 */
fun interface HtmlAttributeAdapter {
  operator fun set(key: HtmlAttributeName, value: String?)
}

/**
 * Type safe wrapper for string that serves as session key.
 */
value class SessionKey(val value: String)

/**
 * Type safe wrapper for string that serves as html attribute name.
 */
value class HtmlAttributeName(val value: String)

/**
 * Creates an adapter for a [Window.sessionStorage] item to be used by [SideEffectBehavior].
 */
val Window.sessionItem: SessionItemAdapter
  get() = object : SessionItemAdapter {
    override fun get(key: SessionKey): String? = this@sessionItem.sessionStorage.getItem(key.value)

    override fun set(key: SessionKey, value: String?) = if (value == null) {
      this@sessionItem.sessionStorage.removeItem(key.value)
    } else {
      this@sessionItem.sessionStorage.setItem(key.value, value)
    }
  }

/**
 * Creates an adapter for a [Document.documentElement] to be used by [SideEffectBehavior].
 */
val Document.htmlAttribute: HtmlAttributeAdapter
  get() = HtmlAttributeAdapter { name, value ->
    this.documentElement?.let {
      if (value != null) {
        it.setAttribute(name.value, value)
      } else {
        it.removeAttribute(name.value)
      }
    }
  }
