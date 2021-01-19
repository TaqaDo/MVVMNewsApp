package com.androiddevs.mvvmnewsapp.repository

import com.androiddevs.mvvmnewsapp.data.local.room.ArticleDatabase
import com.androiddevs.mvvmnewsapp.data.network.RetrofitInstance
import retrofit2.Retrofit

class NewsRepository(
    val db: ArticleDatabase
) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)
}