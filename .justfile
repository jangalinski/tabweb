
set shell := ["zsh", "-lc"]

_list:
    just --list

# Refresh the local Tabler preview reference from its private source repository. The snapshot is intentionally ignored by Git and is only used for local HTML and CSS comparisons.
[group("project")]
fetch-tabler-preview:
    #!/usr/bin/env zsh
    set -euo pipefail
    tmp_dir="$(mktemp -d)"
    trap 'rm -rf "$tmp_dir"' EXIT
    archive="$tmp_dir/preview.tar.gz"
    gh api repos/jangalinski/preview.tabler.io/tarball/main > "$archive"
    archive_root="$(tar -tzf "$archive" | sed -n '1s#/.*##p')"
    test -n "$archive_root"
    rm -rf docs/preview.tabler.io
    mkdir -p docs
    tar -xzf "$archive" -C "$tmp_dir"
    mv "$tmp_dir/$archive_root/site" docs/preview.tabler.io

# Refresh the local KotlinBootstrap reference. The snapshot is intentionally ignored by Git and is only used as a local Kobweb Bootstrap implementation reference.
[group("project")]
fetch-kobweb-bootstrap:
    #!/usr/bin/env zsh
    set -euo pipefail
    tmp_dir="$(mktemp -d)"
    trap 'rm -rf "$tmp_dir"' EXIT
    archive="$tmp_dir/kotlin-bootstrap.tar.gz"
    gh api repos/stevdza-san/KotlinBootstrap/tarball/master > "$archive"
    archive_root="$(tar -tzf "$archive" | sed -n '1s#/.*##p')"
    test -n "$archive_root"
    rm -rf docs/kobweb-bootstrap
    mkdir -p docs
    tar -xzf "$archive" -C "$tmp_dir"
    mv "$tmp_dir/$archive_root/bootstrap" docs/kobweb-bootstrap
    cp "$tmp_dir/$archive_root/README.md" docs/kobweb-bootstrap/README.md

# Run the documentation site via kobweb in static layout.
[group("site")]
run-site:
    @just stop-site
    kobweb run -p site -l static --env=dev

# Export the documentation site using the same static layout as the deployment workflow. Local exports intentionally keep the local base path `/`; only the GitHub Pages workflow adds the repository prefix.
[group("site")]
export-site:
    @just stop-site
    .agents/bin/gradlew-agent --no-watch-fs :site:kobwebExport -PkobwebReuseServer=true -PkobwebEnv=DEV -PkobwebRunLayout=STATIC -PkobwebBuildTarget=RELEASE -PkobwebExportLayout=STATIC --console=plain

# Export and preview the documentation site. Pass `true` to serve the most recent existing export without cleaning or exporting again.
[group("site")]
preview-site skip_export="false":
    @if test "{{ skip_export }}" = "true"; then if ! test -f build/site-preview/index.html; then echo "No previous static export found at build/site-preview. Run 'just export-site' first." >&2; exit 1; fi; else if test "{{ skip_export }}" != "false"; then echo "Usage: just preview-site [true|false]" >&2; exit 2; fi; just clean-preview-artifacts; just export-site; fi
    echo "Preview at http://localhost:13131/"
    python3 -m http.server 13131 --directory ./build/site-preview

# Stop local documentation site servers.
[group("site")]
stop-site:
    just stop

# Stop local kobweb/python servers listening on the preview ports.
[group("kobweb")]
stop:
    @for port in 13130 13131; do pids="$(lsof -tiTCP:$port -sTCP:LISTEN 2>/dev/null || true)"; if [ -n "$pids" ]; then echo "Stopping listeners on port $port: $pids"; kill $pids; fi; done; sleep 1; for port in 13130 13131; do pids="$(lsof -tiTCP:$port -sTCP:LISTEN 2>/dev/null || true)"; if [ -n "$pids" ]; then echo "Force-stopping listeners on port $port: $pids"; kill -9 $pids; fi; done; just clean-preview-artifacts

# Remove stray origin-named preview directories created by local browser or server sessions.
[group("project")]
clean-preview-artifacts:
    @find . -maxdepth 1 -type d \( -name '127.0.0.1:*' -o -name 'localhost:*' \) -prune -exec rm -rf {} +

# Reset all ignored local state while preserving tracked files and unignored files.
[group("project")]
clean mode="":
    @if test "{{ mode }}" = "-n"; then git clean -ndX -- .; elif test -z "{{ mode }}"; then ./gradlew --stop; git clean -fdX -- .; else echo "Usage: just clean [-n]" >&2; exit 2; fi

# generate dokka html
[group("project")]
generate-dokka-html:
    @./gradlew --no-daemon --no-watch-fs --console=plain :lib:dokkaGeneratePublicationHtml

# tabler icon from css
[group("project")]
generate-tabler-icon:
    @./gradlew --no-daemon --no-watch-fs --console=plain :lib:generateTablerIcon

# generate kotlin code for tabler css
[group("project")]
generate-kotlin-code:
    @./gradlew --no-daemon --no-watch-fs --console=plain :lib:generateKotlinCode

# Generate the complete, version-pinned Tabler CSS reference for API planning.
[group("project")]
generate-tabler-css-docs:
    @.agents/bin/gradlew-agent --no-watch-fs --console=plain generateTablerCssDocumentation

# build project
[group("gradle")]
build:
    ./gradlew :lib:build :site:build

# evaluate detekt rules
[group("gradle")]
detekt strict="false":
    ./gradlew --no-daemon --no-watch-fs --console=plain :lib:check -PtablerDetekt.strict={{ strict }} --rerun-tasks

# Export the documentation site from an isolated temporary Kobweb project so an existing run-site server can keep running.
[group("site")]
tmp-export:
    #!/usr/bin/env zsh
    set -euo pipefail

    mkdir -p build/tmp
    tmp_root="$(mktemp -d build/tmp/kobweb-export-root.XXXXXX)"
    tmp_site="$tmp_root/site"
    real_preview="build/site-preview"
    tmp_preview="$tmp_root/build/site-preview"

    echo "Preparing isolated Kobweb export project at $tmp_root"

    cp settings.gradle.kts "$tmp_root/settings.gradle.kts"
    cp build.gradle.kts "$tmp_root/build.gradle.kts"
    cp gradle.properties "$tmp_root/gradle.properties"

    rsync -a \
      --exclude='.gradle' \
      --exclude='build' \
      gradle/ "$tmp_root/gradle/"

    rsync -a \
      --exclude='.gradle' \
      --exclude='build' \
      lib/ "$tmp_root/lib/"

    rsync -a \
      --exclude='.gradle' \
      --exclude='build' \
      --exclude='.kobweb/site' \
      --exclude='.kobweb/server' \
      site/ "$tmp_site/"

    perl -0pi -e 's/(\n  port:\s*)\d+/$1 . "13132"/e' "$tmp_site/.kobweb/conf.yaml"

    .agents/bin/gradlew-agent \
      --no-watch-fs \
      -p "$tmp_root" \
      :site:kobwebExport \
      -PkobwebReuseServer=false \
      -PkobwebEnv=DEV \
      -PkobwebRunLayout=STATIC \
      -PkobwebBuildTarget=RELEASE \
      -PkobwebExportLayout=STATIC \
      --console=plain

    .agents/bin/gradlew-agent --no-watch-fs -p "$tmp_root" :site:kobwebStop --console=plain

    rm -rf "$real_preview"

    if test -d "$tmp_preview"; then
      mkdir -p "$(dirname "$real_preview")"
      cp -R "$tmp_preview" "$real_preview"
    else
      echo "Expected preview output not found at $tmp_preview" >&2
      exit 1
    fi

    echo "Temporary export written to $real_preview"
    rm -rf "$tmp_root"
