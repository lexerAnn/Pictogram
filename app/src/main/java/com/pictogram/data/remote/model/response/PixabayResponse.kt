package com.pictogram.data.remote.model.response

data class PixabayResponse(
    val total: Int,
    val totalHits: Int,
    val hits: List<ImageResult>
)
