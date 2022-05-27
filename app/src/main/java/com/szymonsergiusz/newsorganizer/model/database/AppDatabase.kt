package com.szymonsergiusz.newsorganizer.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.szymonsergiusz.newsorganizer.model.News
import com.szymonsergiusz.newsorganizer.model.NewsFts

@Database(entities = [News::class, NewsFts::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao() : NewsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase {

            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "note_database").fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}