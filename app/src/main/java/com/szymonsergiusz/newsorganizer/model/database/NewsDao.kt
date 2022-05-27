package com.szymonsergiusz.newsorganizer.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.szymonsergiusz.newsorganizer.model.News
import com.szymonsergiusz.newsorganizer.model.NewsFts

@Dao
interface NewsDao {
    @Query("SELECT * FROM news")
    fun getAll(): LiveData<List<News>>

    @Insert
    suspend fun insertNews(news: News)

    @Delete
    suspend fun deleteNews(news: News)

    @Query("""
        SELECT *
        FROM news
        JOIN news_fts ON news.title = news_fts.title 
        WHERE news_fts MATCH :query
    """)
    suspend fun search(query: String): List<News>

//    @Query("SELECT * FROM news WHERE title LIKE :search")
//    suspend fun searchFor(search: String?):LiveData<List<News>> //TODO(search = "%fido%";)
    //może zmienić na rxjave
}