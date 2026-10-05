package com.github.jangalinski.tabweb.element

import kotlinx.browser.window

internal class TooltipRuntimeStub {
  private var previous: dynamic = null
  val state: dynamic = js("""
    (() => {
      const instances = new Map();
      const state = { created: [], disposed: 0, hidden: 0 };
      state.api = {
        getInstance(element) { return instances.get(element) || null; },
        getOrCreateInstance(element, options) {
          if (instances.has(element)) return instances.get(element);
          const instance = {
            hide() { state.hidden++; },
            dispose() {
              state.disposed++;
              instances.delete(element);
            }
          };
          state.created.push({ element, options });
          instances.set(element, instance);
          return instance;
        }
      };
      return state;
    })()
  """)

  fun install() {
    previous = window.asDynamic().tabler
    window.asDynamic().tabler = js("({})")
    window.asDynamic().tabler.Tooltip = state.api
  }

  fun restore() {
    window.asDynamic().tabler = previous
  }
}
