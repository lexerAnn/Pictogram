package com.pictogram.domain.repository

import com.pictogram.data.remote.model.request.LoginRequest
import com.pictogram.data.remote.model.request.RegisterRequest
import com.pictogram.data.remote.model.response.AuthResponse
import com.pictogram.domain.DataState
import kotlinx.coroutines.flow.Flow

interface AuthRepository{
    fun login(loginRequest: LoginRequest): Flow<DataState<AuthResponse>>
    fun register(registerRequest: RegisterRequest): Flow<DataState<AuthResponse>>
}