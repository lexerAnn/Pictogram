package com.pictogram.data.repository

import com.pictogram.data.remote.AuthApi
import com.pictogram.data.remote.model.request.LoginRequest
import com.pictogram.data.remote.model.request.RegisterRequest
import com.pictogram.data.remote.model.response.AuthResponse
import com.pictogram.domain.DataState
import com.pictogram.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi
) : AuthRepository {
    override fun login(loginRequest: LoginRequest): Flow<DataState<AuthResponse>> {
        return flow {
            try {
                emit(DataState.Loading)
                val response = api.login(loginRequest)

                if (response.status == 200) {
                    emit(DataState.Success(response.data))
                } else {
                    emit(DataState.Error(Exception(response.message)))
                }

            } catch (e: UnknownHostException) {
                Timber.tag("requestException").e("$e")
                emit(DataState.Error(Exception("Please check your internet connection and try again")))
            } catch (e: IOException) {
                Timber.tag("requestException").e("$e")
                emit(DataState.Error(Exception(e.message ?: "Check your internet and try again.")))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun register(registerRequest: RegisterRequest): Flow<DataState<AuthResponse>> {
        return flow {
            try {
                emit(DataState.Loading)
                val response = api.register(registerRequest)

                if (response.status == 200) {
                    emit(DataState.Success(response.data))
                } else {
                    emit(DataState.Error(Exception(response.message)))
                }

            } catch (e: UnknownHostException) {
                Timber.tag("requestException").e("$e")
                emit(DataState.Error(Exception("Please check your internet connection and try again")))
            } catch (e: IOException) {
                Timber.tag("requestException").e("$e")
                emit(DataState.Error(Exception(e.message ?: "Check your internet and try again.")))
            }
        }.flowOn(Dispatchers.IO)    }
}