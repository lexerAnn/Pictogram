package com.pictogram.presentation.home.vm

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pictogram.data.remote.model.response.ImageResult
import com.pictogram.domain.UseCases
import com.pictogram.presentation.home.adapters.ImageListPagingAdapter
import com.pictogram.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val useCases: UseCases
): BaseViewModel() {

    val imageAdapter: ObservableField<ImageListPagingAdapter> = ObservableField()


    fun fetchImages(): Flow<PagingData<ImageResult>> {
        return useCases.imagesUseCase().cachedIn(viewModelScope)
    }
}