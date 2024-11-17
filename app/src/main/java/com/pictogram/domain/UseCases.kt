package com.pictogram.domain

import com.pictogram.domain.usecases.ImagesUseCase
import com.pictogram.domain.usecases.LoginUseCase
import com.pictogram.domain.usecases.RegisterUseCase

data class UseCases(
    val loginUseCase: LoginUseCase,
    val registerUseCase: RegisterUseCase,
    val imagesUseCase: ImagesUseCase
)
