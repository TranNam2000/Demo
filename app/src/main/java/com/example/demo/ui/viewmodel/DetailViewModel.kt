package com.example.demo.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.base.BaseViewModel
import com.app.base.common.State
import com.example.demo.model.DeatilResponse
import com.example.demo.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: NewsRepository
) : BaseViewModel() {
    
    private val _detailState = MutableLiveData<State<DeatilResponse>>()
    val detailState: LiveData<State<DeatilResponse>> = _detailState
    
    fun loadDetail() {
        viewModelScope.launch {
            _detailState.value = State.Loading
            try {
                val detail = repository.fetchDetails()
                if (true) {
                    _detailState.value = detail
                } else {
                    _detailState.value = State.Error("Detail not found")
                }
            } catch (e: Exception) {
                _detailState.value = State.Error(e.message ?: "Unknown error")
            }
        }
    }
}
