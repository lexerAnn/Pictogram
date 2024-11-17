package com.pictogram.domain.usecases

import com.pictogram.data.remote.model.request.RegisterRequest
import com.pictogram.data.remote.model.response.AuthResponse
import com.pictogram.domain.DataState
import com.pictogram.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(requestBody: RegisterRequest): Flow<DataState<AuthResponse>> {
        return authRepository.register(requestBody)
    }
}