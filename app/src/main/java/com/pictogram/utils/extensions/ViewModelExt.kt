package com.pictogram.utils.extensions

import androidx.lifecycle.MutableLiveData
import com.pictogram.domain.DataState
import com.pictogram.utils.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

fun <T> BaseViewModel.emitFlowResults(
    liveDataObject: MutableLiveData<DataState<T>>,
    networkRequest: () -> Flow<DataState<T>>
) {
    coroutineScope.launch(Dispatchers.IO) {
        networkRequest()
            .onStart { liveDataObject.postValue(DataState.Loading) }
            .onEach {
                liveDataObject.postValue(it)
            }
            .catch {
                liveDataObject.postValue(DataState.Error(Exception(it.localizedMessage)))
            }
            .launchIn(this)

    }
}
