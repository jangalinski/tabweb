package com.github.jangalinski.tabweb.site.data

import com.github.jangalinski.tabweb._foundation.Image

data object LoremPicsum {
  internal const val WIDTH = 800
  internal const val HEIGHT = 600

  private var counter = 0


  fun image(width: Int = WIDTH, height: Int = HEIGHT): Image.Resource {

    //return Image.invoke(url = "https://loremflickr.com/$width/$height$colorParam$keywordParam", altText = alt)
    return Image(url = "https://picsum.photos/$width/$height?random=${counter++}", altText = "")
  }
}
