package com.example.demo.repository

import android.content.Context
import com.app.base.common.State
import com.example.demo.cache.NewsCache
import com.example.demo.model.DeatilResponse
import com.example.demo.model.NewsItem
import com.example.demo.network.NetworkModule
import javax.inject.Singleton

@Singleton
class NewsRepository(private val context: Context) {
    private val apiService = NetworkModule.apiService
    private val newsCache = NewsCache.getInstance(context)

    suspend fun fetchNewsfeed(): State<List<NewsItem>> {
        return try {
            // Try cache first if valid
            if (newsCache.isCacheValid(NewsCache.KEY_NEWS_FEED)) {
                State.Success(newsCache.getNewsfeed() ?: emptyList())
            } else {
                // Fetch from API
                val newsItems = apiService.getNewsfeed()?.items
                if (newsItems?.isEmpty() == true || newsItems == null) {
                    return State.Error("No news items found")
                }
                newsCache.saveNewsfeed(newsItems)
                State.Success(newsItems)
            }
        } catch (e: Exception) {
            State.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun fetchDetails(): State<DeatilResponse> {
        return try {
            // Try cache first if valid
            if (newsCache.isCacheValid(NewsCache.KEY_DETAILS)) {
                State.Success(newsCache.getDetails() ?: DeatilResponse())
            } else {
                // Fetch from API
                val details = apiService.getDetails()
                if (details == null) {
                    return State.Error("Details not found")
                }
                newsCache.saveDetails(details)
                State.Success( details )
            }
        } catch (e: Exception) {
            State.Error(e.message ?: "Unknown error")
        }
    }

    fun clearCache() {
        newsCache.clearCache()
    }
}
