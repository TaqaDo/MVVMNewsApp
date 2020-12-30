package com.androiddevs.mvvmnewsapp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.androiddevs.mvvmnewsapp.data.models.news.Article
import com.androiddevs.mvvmnewsapp.utills.Converters


@Database(
    entities = [Article::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ArticleDatabase: RoomDatabase(), ArticleDao {
    abstract fun getArticleDao(): ArticleDao

    companion object {
        @Volatile
        private var instance: ArticleDao? = null
        private val LOCK = Any()

        operator fun invoke (context: Context) = instance?: synchronized(LOCK) {
        instance?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "article_db.db"
            ).build()
    }
}