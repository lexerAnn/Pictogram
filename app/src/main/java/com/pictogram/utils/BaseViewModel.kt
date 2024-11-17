package com.pictogram.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.plus
import timber.log.Timber

open class BaseViewModel: ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.tag("coroutineExceptionHandler").e("coroutineExceptionHandler --> $throwable")
    }
    private val job = SupervisorJob()
    private val context = Dispatchers.Main + job + exceptionHandler

    val coroutineScope = (viewModelScope + exceptionHandler)

}