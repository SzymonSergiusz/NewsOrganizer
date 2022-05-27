package com.szymonsergiusz.newsorganizer.model

import org.jsoup.Jsoup


class ScraperSoup {
    fun download(url: String, titleClass: String) : Map<String, String> {
        val map = mutableMapOf<String, String>()

        val doc = Jsoup.connect(url).get()

        val elements = doc.getElementsByClass(titleClass)
        val newsTitles = elements.map {
            val title = it.select("a").eachText().toString().removeSurrounding("[", "]")
            val link = it.select("a").attr("href")
            map.put(title, link)
        }
       return map
    }

}