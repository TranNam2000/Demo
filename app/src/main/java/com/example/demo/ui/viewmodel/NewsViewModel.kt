package com.example.demo.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.base.BaseViewModel
import com.app.base.common.State
import com.example.demo.model.NewsItem
import com.example.demo.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : BaseViewModel() {

    private val _newsState = MutableLiveData<State<List<NewsItem>>>()
    val newsState: LiveData<State<List<NewsItem>>> = _newsState
    
    fun loadNewsfeed() {
        viewModelScope.launch {
            _newsState.value = State.Loading
            
            try {
                val news = repository.fetchNewsfeed()
                _newsState.value = news

            } catch (e: Exception) {
                _newsState.value = State.Error(e.message ?: "Unknown error")
            }
        }
    }
    
    fun refreshNewsfeed() {
        loadNewsfeed()
    }
    
    fun clearCache() {
        repository.clearCache()
    }
}
