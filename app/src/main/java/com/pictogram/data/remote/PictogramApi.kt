package com.pictogram.data.remote

import com.pictogram.BuildConfig
import com.pictogram.data.remote.model.response.PixabayResponse
import com.pictogram.utils.Constants.DEFAULT_PAGE_SIZE
import com.pictogram.utils.Constants.DEFAULT_STARTING_PAGE_INDEX
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PictogramApi {
    @GET("/api/")
    suspend fun fetchImages(
        @Query("page")pageNumber:Int = DEFAULT_STARTING_PAGE_INDEX,
        @Query("key")key:String = BuildConfig.PIXBAY_API_KEY,
        @Query("per_page")pageSize:Int = DEFAULT_PAGE_SIZE
    ): Response<PixabayResponse>
}