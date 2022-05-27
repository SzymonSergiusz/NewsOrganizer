package com.szymonsergiusz.newsorganizer.model

import it.skrape.core.htmlDocument
import it.skrape.fetcher.*
import it.skrape.selects.eachAttribute
import it.skrape.selects.eachClassName
import it.skrape.selects.eachLink
import it.skrape.selects.html5.a
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun main() {
    val x = Scraper()

    val scope = CoroutineScope(Dispatchers.IO)
    scope.launch {
        x.downloadSite("https://news.ycombinator.com")
    }



}

class Scraper {

//    val doc

    fun downloadSite(link: String): Map<String, String> {
        println("start")
        val links: Map<String, String> = skrape(BrowserFetcher) {
            request {
                url = link
            }
            extractIt {
                htmlDocument {
                    a {
                        findAll {
                            eachLink
                        }
                    }

                }
            }
        }
        links.forEach { (text, link) ->
            println("$text ---> $link")
        }
        return links
    }




    private fun getNews() : News {
        return News(title = getTitle(), description = getBody(),url = getLink())
    }

   private fun getTitle() : String {
        return ""
    }

    private fun getBody() : String {
        return ""

    }

    private fun getLink() : String {
        return ""

    }
}