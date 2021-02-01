package com.androiddevs.mvvmnewsapp.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.data.models.news.Article
import com.androiddevs.mvvmnewsapp.data.models.news.NewsResponse
import com.androiddevs.mvvmnewsapp.repository.NewsRepository
import com.androiddevs.mvvmnewsapp.utills.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(
    val newsRepository: NewsRepository
): ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1

    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        try {
            val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
            breakingNews.postValue(handleBreakingNewsResponse(response))
        } catch (ex: Exception) {
            breakingNews.postValue(Resource.Error(ex.message.toString()))
        }


    }


    fun getSearchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        try {
            val response = newsRepository.searchNews(searchQuery, searchNewsPage)
            searchNews.postValue(handleSearchNewsResponse(response))
        } catch (ex: Exception) {
            searchNews.postValue(Resource.Error(ex.message.toString()))
        }

    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }



}