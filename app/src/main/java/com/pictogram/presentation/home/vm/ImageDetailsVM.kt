package com.pictogram.presentation.home.vm

import android.view.View
import androidx.lifecycle.viewModelScope
import com.pictogram.presentation.authentication.events.NavEvent
import com.pictogram.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageDetailsVM  @Inject constructor(): BaseViewModel(){

    private val _navigationEvent = MutableSharedFlow<NavEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun onBackClick(view: View){
        viewModelScope.launch {
            _navigationEvent.emit(NavEvent.NavigateBack)
        }
    }
}