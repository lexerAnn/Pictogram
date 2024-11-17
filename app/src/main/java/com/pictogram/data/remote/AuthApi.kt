package com.pictogram.data.remote

import com.pictogram.data.remote.model.request.LoginRequest
import com.pictogram.data.remote.model.request.RegisterRequest
import com.pictogram.data.remote.model.response.ApiResponse
import com.pictogram.data.remote.model.response.AuthResponse

interface AuthApi {
    suspend fun login(request: LoginRequest): ApiResponse<AuthResponse>
    suspend fun register(request: RegisterRequest): ApiResponse<AuthResponse>
}