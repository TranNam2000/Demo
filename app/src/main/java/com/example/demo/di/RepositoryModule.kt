package com.example.demo.di

import com.example.demo.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    
    @Provides
    @Singleton
    fun provideNewsRepository(
        @ApplicationContext context: android.content.Context
    ): NewsRepository {
        return NewsRepository(context)
    }
}
