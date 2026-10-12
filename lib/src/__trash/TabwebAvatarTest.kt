package com.github.jangalinski.tabweb.avatar

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.doesNotContain
import com.github.jangalinski.tabweb.avatar.TabwebAvatarColor
import com.github.jangalinski.tabweb.avatar.TabwebAvatarContent
import com.github.jangalinski.tabweb.avatar.TabwebAvatarData
import com.github.jangalinski.tabweb.avatar.TabwebAvatarListData
import com.github.jangalinski.tabweb.avatar.TabwebAvatarShape
import com.github.jangalinski.tabweb.avatar.TabwebAvatarSize
import com.github.jangalinski.tabweb.avatar.TabwebAvatarStatus
import com.github.jangalinski.tabweb.avatar.TabwebAvatarStatusColor
import org.jetbrains.compose.web.testutils.ComposeWebExperimentalTestsApi
import org.jetbrains.compose.web.testutils.runTest
import kotlin.test.Ignore
import kotlin.test.Test

@Ignore
@OptIn(ComposeWebExperimentalTestsApi::class)
class TabwebAvatarTest {

  @Test
  fun rendersInitials() = runTest {
    composition {
      TabwebAvatar(TabwebAvatarData(content = TabwebAvatarContent.Initials("JG")))
    }

    val html = root.innerHTML
    assertThat(html).contains("avatar")
    assertThat(html).contains("JG")
  }

  @Test
  fun rendersImageResource() = runTest {
    composition {
      TabwebAvatar(TabwebAvatarData(content = TabwebAvatarContent.ImageResource("images/user.jpg", altText = "User photo")))
    }

    val html = root.innerHTML
    assertThat(html).contains("avatar")
    assertThat(html).contains("<img")
    assertThat(html).contains("user.jpg")
    assertThat(html).contains("User photo")
  }

  @Test
  fun rendersImageResourceWithoutAltText() = runTest {
    composition {
      TabwebAvatar(TabwebAvatarData(content = TabwebAvatarContent.ImageResource("images/anon.png")))
    }

    val html = root.innerHTML
    assertThat(html).contains("<img")
    assertThat(html).contains("anon.png")
  }

  @Test
  fun rendersIconContent() = runTest {
    val svg = """<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><circle cx="12" cy="12" r="10"/></svg>"""
    composition {
      TabwebAvatar(TabwebAvatarData(content = TabwebAvatarContent.Icon(svg, altText = "Circle icon")))
    }

    val html = root.innerHTML
    assertThat(html).contains("avatar")
    assertThat(html).contains("<img")
    assertThat(html).contains("data:image/svg+xml")
    assertThat(html).contains("Circle icon")
  }

  @Test
  fun rendersIconWithIconClass() = runTest {
    val svg = """<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><rect width="24" height="24"/></svg>"""
    composition {
      TabwebAvatar(TabwebAvatarData(content = TabwebAvatarContent.Icon(svg)))
    }

    val html = root.innerHTML
    assertThat(html).contains("icon")
  }

  @Test
  fun rendersColorClasses() = runTest {
    composition {
      TabwebAvatar(
        TabwebAvatarData(
          content = TabwebAvatarContent.Initials("AB"),
          color = TabwebAvatarColor.BLUE,
        ),
      )
    }

    val html = root.innerHTML
    assertThat(html).apply {
      contains("bg-blue")
      contains("text-white")
    }
  }

  @Test
  fun rendersSizeClass() = runTest {
    composition {
      TabwebAvatar(
        TabwebAvatarData(
          content = TabwebAvatarContent.Initials("XL"),
          size = TabwebAvatarSize.EXTRA_LARGE,
        ),
      )
    }

    val html = root.innerHTML
    assertThat(html).contains("avatar-xl")
  }

  @Test
  fun rendersShapeClass() = runTest {
    composition {
      TabwebAvatar(
        TabwebAvatarData(
          content = TabwebAvatarContent.Initials("CI"),
          shape = TabwebAvatarShape.CIRCLE,
        ),
      )
    }

    val html = root.innerHTML
    assertThat(html).contains("rounded-circle")
  }

  @Test
  fun rendersAriaLabel() = runTest {
    composition {
      TabwebAvatar(
        TabwebAvatarData(
          content = TabwebAvatarContent.Initials("AL"),
          ariaLabel = "Profile picture",
        ),
      )
    }

    val html = root.innerHTML
    assertThat(html).contains("aria-label")
    assertThat(html).contains("Profile picture")
  }

  @Test
  fun rendersStatusBadge() = runTest {
    composition {
      TabwebAvatar(
        TabwebAvatarData(
          content = TabwebAvatarContent.Initials("SB"),
          status = TabwebAvatarStatus(color = TabwebAvatarStatusColor.SUCCESS),
        ),
      )
    }

    val html = root.innerHTML
    assertThat(html).contains("bg-success")
  }

  @Test
  fun rendersAvatarListWithStackedClass() = runTest {
    val avatars = listOf(
      TabwebAvatarData(content = TabwebAvatarContent.Initials("A1")),
      TabwebAvatarData(content = TabwebAvatarContent.Initials("A2")),
    )

    composition {
      TabwebAvatarList(TabwebAvatarListData(avatars = avatars, stacked = true))
    }

    val html = root.innerHTML
    assertThat(html).contains("avatar-list")
    assertThat(html).contains("avatar-list-stacked")
    assertThat(html).contains("A1")
    assertThat(html).contains("A2")
  }

  @Test
  fun rendersAvatarListWithoutStackedClassByDefault() = runTest {
    val avatars = listOf(TabwebAvatarData(content = TabwebAvatarContent.Initials("X")))

    composition {
      TabwebAvatarList(TabwebAvatarListData(avatars = avatars))
    }

    val html = root.innerHTML
    assertThat(html).contains("avatar-list")
    assertThat(html).doesNotContain("avatar-list-stacked")
  }
}
