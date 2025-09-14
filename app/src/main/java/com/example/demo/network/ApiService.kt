package com.example.demo.network

import com.example.demo.model.DeatilResponse
import com.example.demo.model.NewsResponse
import retrofit2.http.GET

interface ApiService {
    @GET("newsfeed.json")
    suspend fun getNewsfeed(): NewsResponse?

    @GET("detail.json")
    suspend fun getDetails(): DeatilResponse?
}
