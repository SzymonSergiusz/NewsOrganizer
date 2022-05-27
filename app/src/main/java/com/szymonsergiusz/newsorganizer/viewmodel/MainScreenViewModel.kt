package com.szymonsergiusz.newsorganizer.viewmodel

import android.app.Application
import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.szymonsergiusz.newsorganizer.model.News
import com.szymonsergiusz.newsorganizer.model.Scraper
import com.szymonsergiusz.newsorganizer.model.ScraperSoup
import com.szymonsergiusz.newsorganizer.model.database.AppDatabase
import com.szymonsergiusz.newsorganizer.model.database.NewsDao
import com.szymonsergiusz.newsorganizer.model.database.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainScreenViewModel(app: Application) : ViewModel() {

    private val repository: NewsRepository


    private val newsDao: NewsDao
     var allNews: LiveData<List<News>>

    private val _searchResults = MutableLiveData<List<News>>()
    val searchResults: LiveData<List<News>>
        get() = _searchResults




    init {
        val appDatabase = AppDatabase.getDatabase(app)
        newsDao = appDatabase.newsDao()
        repository = NewsRepository(newsDao)

        allNews = repository.allNews

        fetchData()
    }

    private fun fetchData() {
//        repository.allNews.let { _searchResults.value = it.value }
    }

    fun insertNews(news: News) {
        repository.insertNews(news)
    }

    fun deleteNews(news: News) {
        repository.deleteNews(news)
    }

    fun search(query: String?) {
        viewModelScope.launch {
            if (query.isNullOrBlank()) {
                newsDao.getAll().let {
                    _searchResults.value = it.value
                }
            } else {
                newsDao.search("*$query*").let {
                    _searchResults.value = it
                }
            }
        }
    }

    fun downloadNews() {
        val list = listOf<SiteToScrape>(SiteToScrape("https://news.ycombinator.com", "title", ""))

        val site = list[0]

        val scrape = ScraperSoup()

        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val map = scrape.download(site.url, "titleLink")

            map.forEach { (text, link) ->
                insertNews(News(title = text, description = "", url = link))

            }
        }


    }

//    fun search(query: String?) {
//        viewModelScope.launch {
//            if (query.isNullOrBlank()) {
//                newsDao.getAll().let {
//                    _searchResults.value = it.value
//                }
//            } else {
//                newsDao.search("*$query*").let {
//                    _searchResults.value = it
//                }
//            }
//        }
//    }
}