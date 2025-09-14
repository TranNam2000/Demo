package com.example.demo.cache

import android.content.Context
import com.example.demo.model.DeatilResponse
import com.example.demo.model.NewsItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class NewsCache private constructor(context: Context) {

    private val cacheDir: File
    private val gson = Gson()

    init {
        cacheDir = File(context.cacheDir, "news_cache")
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }
    }

    fun saveNewsfeed(newsItems: List<NewsItem>?) {
        try {
            val file = File(cacheDir, "newsfeed.json")
            val writer = FileWriter(file)
            gson.toJson(newsItems, writer)
            writer.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getNewsfeed(): List<NewsItem>? {
        return try {
            val file = File(cacheDir, "newsfeed.json")
            if (file.exists()) {
                val reader = FileReader(file)
                val type = object : TypeToken<List<NewsItem>>() {}.type
                gson.fromJson<List<NewsItem>>(reader, type)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun saveDetails(details: DeatilResponse?) {
        try {
            val file = File(cacheDir, "details.json")
            val writer = FileWriter(file)
            gson.toJson(details, writer)
            writer.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getDetails(): DeatilResponse? {
        return try {
            val file = File(cacheDir, "details.json")
            if (file.exists()) {
                val reader = FileReader(file)
                val type = object : TypeToken<DeatilResponse>() {}.type
                gson.fromJson<DeatilResponse>(reader, type)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getDetailById(): DeatilResponse? {
        return getDetails()
    }

    fun clearCache() {
        cacheDir.listFiles()?.forEach { it.delete() }
    }

    fun isCacheValid(
        key: String,
        maxAgeMillis: Long = 5 * 60 * 1000
    ): Boolean { // 5 minutes default
        val cache = File(cacheDir, key)
        val detailsFile = File(cacheDir, "details.json")

        val timeCacheAge = if (cache.exists()) {
            System.currentTimeMillis() - cache.lastModified()
        } else {
            Long.MAX_VALUE
        }



        return timeCacheAge < maxAgeMillis
    }

    companion object {
        @Volatile
        private var INSTANCE: NewsCache? = null

        fun getInstance(context: Context): NewsCache {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: NewsCache(context.applicationContext).also { INSTANCE = it }
            }
        }

        const val KEY_NEWS_FEED = "newsfeed.json"
        const val KEY_DETAILS = "details.json"
    }


}
