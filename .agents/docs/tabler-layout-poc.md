# Tabler Layout POC Summary

This note records the first pass of the shared `TabwebLayout` implementation.

## What it does

- Renders the shared Tabler page shell.
- Reads `TablerLayoutData` and `TablerPageMeta` from Kobweb route data.
- Keeps the layout generic so the site module can provide its own nav content.

## Why it exists

- Kobweb layouts are the right place for shared page chrome.
- `@InitRoute` is the Kobweb mechanism for getting page-specific data into the layout before render.
- The layout needs a stable seam so centralized navigation can later be generated and injected without rewriting the shell.

## What was executed

- `TabwebLayout` was expanded to own the full Tabler shell directly.
- The site module now provides page metadata through `@InitRoute`.
- The first site pages compile and export successfully with the new layout flow.
