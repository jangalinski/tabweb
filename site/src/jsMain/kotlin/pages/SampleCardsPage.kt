package com.github.jangalinski.tabweb.site.pages

import androidx.compose.runtime.Composable
import com.github.jangalinski.tabweb.Tabweb.button
import com.github.jangalinski.tabweb.Tabweb.buttons
import com.github.jangalinski.tabweb.Tabweb.cardGroup
import com.github.jangalinski.tabweb.Tabweb.cardRow
import com.github.jangalinski.tabweb._foundation.Link
import com.github.jangalinski.tabweb._foundation.Url
import com.github.jangalinski.tabweb._foundation.compose.KDiv
import com.github.jangalinski.tabweb._foundation.compose.KP
import com.github.jangalinski.tabweb._foundation.compose.KText
import com.github.jangalinski.tabweb._foundation.css.cssClass
import com.github.jangalinski.tabweb._foundation.css.plus
import com.github.jangalinski.tabweb._foundation.modifier.BackgroundColor
import com.github.jangalinski.tabweb.button.ButtonColor
import com.github.jangalinski.tabweb.button.ButtonStyle
import com.github.jangalinski.tabweb.card.*
import com.github.jangalinski.tabweb.icon.TablerIcon
import com.github.jangalinski.tabweb.site.SiteRoutes
import com.github.jangalinski.tabweb.site.data.LoremPicsum
import com.github.jangalinski.tabweb.site.siteLayoutData
import com.github.jangalinski.tabweb.site.sitePageMeta
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext

private val sampleLink = object : Link {
  override val href = Url(SiteRoutes.Cards)
  override val text = "Link"
}

@InitRoute
fun initCardsPage(ctx: InitRouteContext) {
  ctx.data.add(sitePageMeta("Cards", "Cards are flexible and extensible content containers with multiple variants and options."))
  ctx.data.add(siteLayoutData(SiteRoutes.Cards))
}

@Page(routeOverride = SiteRoutes.Cards)
@Composable
fun SampleCardsPage() {
  cardRow {

    // 1. Headers & body styles
    card(modifier = cssClass("col-12")) {
      body {
        title("Headers & body styles")
        subtitle("Combine a header, a tinted background, or drop the border for a simpler look.")
        cardRow {
          card(modifier = cssClass("col-md-6") + cssClass("col-lg-3")) {
            header(title = "Card title")
            body { KText("Simple card") }
          }
          card(modifier = cssClass("col-md-6") + cssClass("col-lg-3")) {
            header(title = "Card title", light = true)
            body { KText("Card with header background") }
          }
          card(modifier = cssClass("col-md-6") + cssClass("col-lg-3"), borderless = true) {
            body {
              title("Card title")
              KText("Card without border")
            }
          }
          card(modifier = cssClass("col-md-6") + cssClass("col-lg-3")) {
            header(title = "Card title", subtitle = "Subtitle")
            body { KText("Card with title and subtitle") }
          }
        }
      }
    }

    // 2. Link hover effects
    card(modifier = cssClass("col-12")) {
      body {
        title("Link hover effects")
        subtitle("Add motion cues when the whole card acts as a link.")
        cardRow {
          card(modifier = cssClass("col-md-6") + cssClass("col-lg-3"), link = sampleLink, linkType = CardLinkType.DEFAULT) {
            body { KText("Default hover effect") }
          }
          card(modifier = cssClass("col-md-6") + cssClass("col-lg-3"), link = sampleLink, linkType = CardLinkType.ROTATE) {
            body { KText("Rotate hover effect") }
          }
          card(modifier = cssClass("col-md-6") + cssClass("col-lg-3"), link = sampleLink, linkType = CardLinkType.POP) {
            body { KText("Pop hover effect") }
          }
        }
      }
    }

    // 3. Rotation & state
    card(modifier = cssClass("col-12")) {
      body {
        title("Rotation & state")
        subtitle("Tilt a card slightly, or mark it as active or inactive.")
        cardRow {
          card(modifier = cssClass("col-md-6") + cssClass("col-lg-3"), rotate = CardRotate.END) {
            body { KText("Card rotate end") }
          }
          card(modifier = cssClass("col-md-6") + cssClass("col-lg-3"), rotate = CardRotate.START) {
            body { KText("Card rotate start") }
          }
          card(modifier = cssClass("col-md-6") + cssClass("col-lg-3"), active = true) {
            body { KP { KText("This is a card with active state.") } }
          }
          card(modifier = cssClass("col-md-6") + cssClass("col-lg-3"), inactive = true) {
            body { KP { KText("This is some text inactive state.") } }
          }
        }
      }
    }

    // 4. Icon & background accents
    card(modifier = cssClass("col-12")) {
      body {
        title("Icon & background accents")
        subtitle("Draw attention with a stamp icon or a tinted background.")
        cardRow {
          card(
            modifier = cssClass("col-md-6") + cssClass("col-lg-4"),
            stamp = CardStamp(icon = TablerIcon.TI_BELL, color = BackgroundColor.BASE.YELLOW, size = CardStampSize.DEFAULT),
          ) {
            body {
              title("Card with icon bg")
              KP(modifier = cssClass("text-secondary")) {
                KText("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Architecto at consectetur culpa ducimus eum fuga fugiat, ipsa iusto, modi nostrum recusandae reiciendis saepe.")
              }
            }
          }
          card(
            modifier = cssClass("col-md-6") + cssClass("col-lg-4"),
            cardModifier = BackgroundColor.LIGHT.BLUE,
          ) {
            body {
              title("Card with primary light background")
              KP(modifier = cssClass("text-secondary")) {
                KText("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Architecto at consectetur culpa ducimus eum fuga fugiat, ipsa iusto, modi nostrum recusandae reiciendis saepe.")
              }
            }
          }
          card(
            modifier = cssClass("col-md-6") + cssClass("col-lg-4"),
            stamp = CardStamp(icon = TablerIcon.TI_STAR, color = BackgroundColor.BASE.GREEN, size = CardStampSize.LG),
          ) {
            body {
              title("Card with large stamp")
              KP(modifier = cssClass("text-secondary")) {
                KText("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Architecto at consectetur culpa ducimus eum fuga fugiat, ipsa iusto, modi nostrum recusandae reiciendis saepe.")
              }
            }
          }
        }
      }
    }

    // 5. Status indicators
    card(modifier = cssClass("col-12")) {
      body {
        title("Status indicators")
        subtitle("Mark a card's state with a colored edge on any side.")
        cardRow {
          card(modifier = cssClass("col-md-6") + cssClass("col-lg-4"), status = CardStatus.top(BackgroundColor.SEMANTIC.DANGER)) {
            body {
              title("Card with top status")
              KP(modifier = cssClass("text-secondary")) {
                KText("This card shows a top edge indicator.")
              }
            }
          }
          card(modifier = cssClass("col-md-6") + cssClass("col-lg-4"), status = CardStatus.bottom(BackgroundColor.SEMANTIC.SUCCESS)) {
            body {
              title("Card with bottom status")
              KP(modifier = cssClass("text-secondary")) {
                KText("This card shows a bottom edge indicator.")
              }
            }
          }
          card(modifier = cssClass("col-md-6") + cssClass("col-lg-4"), status = CardStatus.start(BackgroundColor.SEMANTIC.PRIMARY)) {
            body {
              title("Card with side status")
              KP(modifier = cssClass("text-secondary")) {
                KText("This card shows a left side edge indicator.")
              }
            }
          }
        }
      }
    }

    // 6. Ribbons & progress
    card(modifier = cssClass("col-12")) {
      body {
        title("Ribbons & progress")
        subtitle("Flag content with a ribbon, or track progress right inside the card.")
        cardRow {
          card(
            modifier = cssClass("col-md-6") + cssClass("col-lg-3"),
            ribbon = CardRibbon(
              icon = TablerIcon.TI_STAR,
              position = CardRibbonPosition.TOP,
              bookmark = true,
              color = BackgroundColor.BASE.YELLOW,
            ),
          ) {
            body {
              title("Card with top ribbon")
              KP(modifier = cssClass("text-secondary")) {
                KText("This card displays a bookmark ribbon on top.")
              }
            }
          }
          card(
            modifier = cssClass("col-md-6") + cssClass("col-lg-3"),
            ribbon = CardRibbon(
              text = "NEW",
              color = BackgroundColor.SEMANTIC.DANGER,
            ),
          ) {
            body {
              title("Card with text ribbon")
              KP(modifier = cssClass("text-secondary")) {
                KText("This card displays a text badge ribbon.")
              }
            }
          }
          card(modifier = cssClass("col-md-6") + cssClass("col-lg-3")) {
            progress(value = 38, color = BackgroundColor.SEMANTIC.PRIMARY)
            body {
              title("Card with progress bar")
              KP(modifier = cssClass("text-secondary")) {
                KText("This card tracks completion at the top edge.")
              }
            }
          }
          card(modifier = cssClass("col-md-6") + cssClass("col-lg-3"), stacked = true) {
            body {
              title("Stacked card")
              KP(modifier = cssClass("text-secondary")) {
                KText("This card renders a layered stack appearance.")
              }
            }
          }
        }
      }
    }

    // 7. Card images
    card(modifier = cssClass("col-12")) {
      body {
        title("Card images")
        subtitle("Pair a card with an image on the left, right, top, or bottom.")

        cardRow {
          card(modifier = cssClass("col-lg-6")) {
            KDiv(modifier = cssClass("row") + cssClass("row-0")) {
              KDiv(modifier = cssClass("col-3")) {
                imageStart(
                  src = LoremPicsum.image().url.get(),
                  alt = "Start Image",
                  modifier = cssClass("w-100") + cssClass("h-100") + cssClass("object-cover"),
                )
              }
              KDiv(modifier = cssClass("col")) {
                body {
                  title("Card with left side image")
                  KP(modifier = cssClass("text-secondary")) {
                    KText("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aperiam deleniti fugit incidunt, iste, itaque minima neque pariatur perferendis sed suscipit velit vitae voluptatem.")
                  }
                }
              }
            }
          }

          card(modifier = cssClass("col-lg-6")) {
            KDiv(modifier = cssClass("row") + cssClass("row-0")) {
              KDiv(modifier = cssClass("col-3") + cssClass("order-md-last")) {
                imageEnd(
                  image = LoremPicsum.image(),
                  modifier = cssClass("w-100") + cssClass("h-100") + cssClass("object-cover"),
                )
              }
              KDiv(modifier = cssClass("col")) {
                body {
                  title("Card with right side image")
                  KP(modifier = cssClass("text-secondary")) {
                    KText("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aperiam deleniti fugit incidunt, iste, itaque minima neque pariatur perferendis sed suscipit velit vitae voluptatem.")
                  }
                }
              }
            }
          }
          card(modifier = cssClass("col-md-6") + cssClass("col-lg-3")) {
            image(image = LoremPicsum.image().copy(altText = "Card with top image"), position = CardImagePosition.TOP)
            body {
              title("Card with top image")
              KP(modifier = cssClass("text-secondary")) {
                KText("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aperiam deleniti fugit incidunt, iste, itaque minima neque pariatur perferendis sed suscipit velit vitae voluptatem.")
              }
            }
          }
          card(modifier = cssClass("col-md-6") + cssClass("col-lg-3")) {
            body {
              title("Card with bottom image")
              KP(modifier = cssClass("text-secondary")) {
                KText("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aperiam deleniti fugit incidunt, iste, itaque minima neque pariatur perferendis sed suscipit velit vitae voluptatem.")
              }
            }
            image(image = LoremPicsum.image(), position = CardImagePosition.BOTTOM)
          }
        }
      }
    }

    // 8. Footers
    card(modifier = cssClass("col-12")) {
      body {
        title("Footers")
        subtitle("Add a footer for metadata, a single action, or a group of buttons.")

        cardRow {
          card(modifier = cssClass("col-md-6") + cssClass("col-lg-3")) {
            body {
              title("Card with footer")
              KP(modifier = cssClass("text-secondary")) {
                KText("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aperiam deleniti fugit incidunt.")
              }
            }
            footer {
              KText("This is standard card footer")
            }
          }
          card(modifier = cssClass("col-md-6") + cssClass("col-lg-3")) {
            body {
              title("Card with transparent footer")
              KP(modifier = cssClass("text-secondary")) {
                KText("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aperiam deleniti fugit incidunt.")
              }
            }
            footer(transparent = true) {
              KText("This is transparent card footer")
            }
          }
          card(modifier = cssClass("col-md-6") + cssClass("col-lg-3")) {
            body {
              title("Card with footer button")
              KP(modifier = cssClass("text-secondary")) {
                KText("Lorem ipsum dolor sit amet, consectetur adipisicing elit.")
              }
            }
            footer {
              button(text = "Action button", color = ButtonColor.PRIMARY)
            }
          }
          card(modifier = cssClass("col-md-6") + cssClass("col-lg-3")) {
            body {
              title("Card with footer buttons")
              KP(modifier = cssClass("text-secondary")) {
                KText("Lorem ipsum dolor sit amet, consectetur adipisicing elit.")
              }
            }
            footer {
              buttons {
                button(text = "Cancel", style = ButtonStyle.GHOST)
                button(text = "Submit", color = ButtonColor.PRIMARY)
              }
            }
          }
        }
      }
    }

    // 9. Nested & grouped cards
    card(modifier = cssClass("col-12")) {
      body {
        title("Nested & grouped cards")
        subtitle("Combine cards inside a card, a full-bleed group, or a matched-height deck.")
        cardRow {
          card(modifier = cssClass("col-12")) {
            header(title = "Cards inside card")
            body {
              cardRow {
                listOf(
                  Triple("First", CardStatusPosition.TOP, BackgroundColor.SEMANTIC.DANGER),
                  Triple("Second", CardStatusPosition.TOP, BackgroundColor.SEMANTIC.SUCCESS),
                  Triple("Third", CardStatusPosition.TOP, BackgroundColor.SEMANTIC.INFO),
                ).forEach { (name, pos, color) ->
                  card(modifier = cssClass("col-md"), status = CardStatus(pos, color)) {
                    header(title = "$name card")
                    body {
                      KP(modifier = cssClass("text-secondary")) {
                        KText("Nested card content for $name.")
                      }
                    }
                  }
                }
              }
            }
          }

          col(modifier = cssClass("col-12")) {
            cardGroup {
              listOf("First", "Second", "Third").forEach { name ->
                card {
                  header(title = "$name card")
                  body {
                    KP(modifier = cssClass("text-secondary")) {
                      KText("Grouped seamless card content.")
                    }
                  }
                }
              }
            }
          }

          col(modifier = cssClass("col-12")) {
            cardRow(deck = true) {
              card(modifier = cssClass("col-md-4")) {
                header(title = "Deck card 1")
                body {
                  KText("This is a wider card with supporting text below as a natural lead-in to additional content.")
                }
                footer {
                  KText("Last updated 3 mins ago")
                }
              }
              card(modifier = cssClass("col-md-4")) {
                header(title = "Deck card 2")
                body {
                  KText("This card has supporting text below as a natural lead-in to additional content.")
                }
                footer {
                  KText("Last updated 3 mins ago")
                }
              }
              card(modifier = cssClass("col-md-4")) {
                header(title = "Deck card 3")
                body {
                  KText("This is a wider card with supporting text below as a natural lead-in to additional content. This card has even longer content to demonstrate equal height action.")
                }
                footer {
                  KText("Last updated 3 mins ago")
                }
              }
            }
          }
        }
      }
    }
  }
}
