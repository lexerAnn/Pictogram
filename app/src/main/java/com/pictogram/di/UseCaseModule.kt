package com.pictogram.di

import com.pictogram.domain.UseCases
import com.pictogram.domain.repository.AuthRepository
import com.pictogram.domain.repository.ImagesRepository
import com.pictogram.domain.usecases.ImagesUseCase
import com.pictogram.domain.usecases.LoginUseCase
import com.pictogram.domain.usecases.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @ViewModelScoped
    @Provides
    fun providesUseCases(
        authRepository: AuthRepository,
        imagesRepository: ImagesRepository
    ): UseCases {
        return UseCases(
            loginUseCase = LoginUseCase(authRepository),
            registerUseCase = RegisterUseCase(authRepository),
            imagesUseCase = ImagesUseCase(imagesRepository)
        )
    }
}