package com.pictogram.data.remote.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pictogram.data.remote.PictogramApi
import com.pictogram.data.remote.model.response.ImageResult
import com.pictogram.utils.Constants
import okio.IOException
import retrofit2.HttpException


class ImagesPagingSource(private val pictogramApi: PictogramApi) :
    PagingSource<Int, ImageResult>() {
    override fun getRefreshKey(state: PagingState<Int, ImageResult>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageResult> {

        val page = params.key ?: Constants.DEFAULT_STARTING_PAGE_INDEX

        return try {
            val response =
              pictogramApi.fetchImages(
                    pageNumber = page,
                    pageSize = params.loadSize
              )
            val imagesList = response.body()?.hits


            val nextKey = if (imagesList?.isEmpty() == true) null else page + 1
            val prevKey = if (page == Constants.DEFAULT_STARTING_PAGE_INDEX) null else page - 1

            LoadResult.Page(
                data = imagesList?: emptyList(),
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        }

    }
}