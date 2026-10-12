# Package com.github.jangalinski.tabweb._foundation.compose

Internal Compose/Kobweb DOM adapter layer. It provides `KDiv`, `KSpan`, `KAnchor`, and related helpers which accept Kobweb `TabwebModifier` values directly.

Library renderers use this DSL whenever it covers the required element, keeping Compose HTML and attribute conversion in one place.
