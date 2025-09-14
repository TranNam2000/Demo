package com.app.base

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel() : ViewModel() {

    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    protected lateinit var context : Context

    open fun init(context:Context){
        this.context = context
    }

}