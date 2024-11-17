package com.pictogram.domain.repository

import androidx.paging.PagingData
import com.pictogram.data.remote.model.response.ImageResult
import kotlinx.coroutines.flow.Flow

interface ImagesRepository {
    fun fetchImages(): Flow<PagingData<ImageResult>>
}