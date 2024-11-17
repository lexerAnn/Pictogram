package com.pictogram.data.repository

import com.pictogram.data.remote.AuthApi
import com.pictogram.data.remote.model.request.LoginRequest
import com.pictogram.data.remote.model.request.RegisterRequest
import com.pictogram.data.remote.model.response.ApiResponse
import com.pictogram.data.remote.model.response.Auth
import com.pictogram.data.remote.model.response.AuthResponse
import kotlinx.coroutines.delay
import javax.inject.Inject

class AuthApiImpl @Inject constructor() :AuthApi {
    override suspend fun login(request: LoginRequest): ApiResponse<AuthResponse> {
        delay(2000)
        return when {
            request.email == "kenannan005@gmail.com" && request.password == "123456" -> {
                ApiResponse(
                    status = 200,
                    message = "Success",
                    data = AuthResponse(
                        id = 1,
                        email = request.email,
                        auth = Auth(
                            accessToken = "wwee",
                            refreshToken = "erde"
                        )
                    )
                )
            }
            else -> {
                ApiResponse(
                    status = 401,
                    message = "Invalid Credentials, Try Again",
                    data = null
                )
            }
        }
    }

    override suspend fun register(request: RegisterRequest): ApiResponse<AuthResponse> {
        delay(2000)
        return ApiResponse(
            status = 200,
            message = "Success",
            data = AuthResponse(
                id = 1,
                email = request.email,
                auth = Auth(
                    accessToken = "wwee",
                    refreshToken = "erde"
                )
            )
        )
    }
}
