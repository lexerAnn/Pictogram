package com.pictogram.domain.usecases

import com.pictogram.data.remote.model.request.LoginRequest
import com.pictogram.data.remote.model.response.AuthResponse
import com.pictogram.domain.DataState
import com.pictogram.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(requestBody: LoginRequest): Flow<DataState<AuthResponse>> {
        return authRepository.login(requestBody)
    }
}