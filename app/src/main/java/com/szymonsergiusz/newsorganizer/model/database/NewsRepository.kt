package com.szymonsergiusz.newsorganizer.model.database

import androidx.lifecycle.LiveData
import com.szymonsergiusz.newsorganizer.model.News
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsRepository(private val dao: NewsDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val allNews = dao.getAll()



    fun insertNews(news: News) {
        coroutineScope.launch(Dispatchers.IO) {
            dao.insertNews(news)
        }
    }

    fun deleteNews(news: News) {
        coroutineScope.launch(Dispatchers.IO) {
            dao.deleteNews(news)
        }
    }

//    fun searchFor(search: String) {
//        coroutineScope.launch(Dispatchers.IO) {
//            dao.searchFor("%$search%")
//        }
//    }
}