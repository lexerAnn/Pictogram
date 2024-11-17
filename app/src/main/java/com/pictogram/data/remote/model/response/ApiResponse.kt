package com.pictogram.data.remote.model.response

data class ApiResponse<T>(
    val status: Int,
    val message: String,
    val data: T?,
)
