package com.example.demo.di

import com.example.demo.network.ApiService
import com.example.demo.network.NetworkModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    
    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return NetworkModule.apiService
    }
}
