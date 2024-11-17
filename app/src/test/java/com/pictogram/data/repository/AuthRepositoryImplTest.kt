package com.pictogram.data.repository


import com.pictogram.data.remote.AuthApi
import com.pictogram.data.remote.model.request.LoginRequest
import com.pictogram.data.remote.model.request.RegisterRequest
import com.pictogram.data.remote.model.response.ApiResponse
import com.pictogram.data.remote.model.response.Auth
import com.pictogram.data.remote.model.response.AuthResponse
import com.pictogram.domain.DataState
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class AuthRepositoryTest {

    @Mock
    private lateinit var authApi: AuthApi
    private lateinit var repository: AuthRepositoryImpl

    private val testEmail = "test@email.com"
    private val testPassword = "password"
    private val testAge = "44"

    private val loginRequest by lazy {
        LoginRequest(testEmail, testPassword)
    }

    private val registerRequest by lazy {
        RegisterRequest(
            email = testEmail,
            password = testPassword,
            age = testAge
        )
    }

    private val successAuthResponse by lazy {
        AuthResponse(
            id = 1,
            email = testEmail,
            auth = Auth(
                accessToken = "token",
                refreshToken = "refresh"
            )
        )
    }

    private val successApiResponse by lazy {
        ApiResponse(
            status = 200,
            message = "Success",
            data = successAuthResponse
        )
    }

    private val errorApiResponse by lazy {
        ApiResponse<AuthResponse>(
            status = 401,
            message = "Invalid credentials",
            data = null
        )
    }

    @Before
    fun setup() {
        repository = AuthRepositoryImpl(authApi)
    }

    @Test
    fun `when login is successful, returns success state`()  = runTest{
        `when`(authApi.login(loginRequest)).thenReturn(successApiResponse)

        val result = repository.login(loginRequest).toList()

        assert(result[0] is DataState.Loading)
        assert(result[1] is DataState.Success)
        assert((result[1] as DataState.Success).data == successApiResponse.data)
    }

    @Test
    fun `when login fails with 401, returns error state`() = runTest{
        `when`(authApi.login(loginRequest)).thenReturn(errorApiResponse)

        val result = repository.login(loginRequest).toList()

        assert(result[0] is DataState.Loading)
        assert(result[1] is DataState.Error)
        assert((result[1] as DataState.Error).exception.message == "Invalid credentials")
    }

    @Test
    fun `when register is successful, returns success state`() = runTest{
        `when`(authApi.register(registerRequest)).thenReturn(successApiResponse)

        val result = repository.register(registerRequest).toList()

        assert(result[0] is DataState.Loading)
        assert(result[1] is DataState.Success)
        assert((result[1] as DataState.Success).data == successApiResponse.data)
    }

    @Test
    fun `when register fails, returns error state`() = runTest{
        // Given
        val registerErrorResponse = ApiResponse<AuthResponse>(
            status = 400,
            message = "Registration failed",
            data = null
        )
        `when`(authApi.register(registerRequest)).thenReturn(registerErrorResponse)

        val result = repository.register(registerRequest).toList()

        assert(result[0] is DataState.Loading)
        assert(result[1] is DataState.Error)
        assert((result[1] as DataState.Error).exception.message == "Registration failed")
    }
}