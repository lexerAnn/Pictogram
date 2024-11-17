package com.pictogram.data.repository

import androidx.paging.PagingData
import com.pictogram.data.remote.PictogramApi
import com.pictogram.data.remote.model.response.ImageResult
import com.pictogram.data.remote.pagination.ImagesPagingSource
import com.pictogram.data.remote.pagination.pagingFlow
import com.pictogram.domain.repository.ImagesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val pictogramApi: PictogramApi

): ImagesRepository {
    override fun fetchImages():  Flow<PagingData<ImageResult>>{
        return pagingFlow { ImagesPagingSource(pictogramApi) }
    }
}