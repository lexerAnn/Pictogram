package com.pictogram.di

import com.pictogram.data.repository.AuthRepositoryImpl
import com.pictogram.data.remote.AuthApi
import com.pictogram.data.remote.PictogramApi
import com.pictogram.data.repository.ImageRepositoryImpl
import com.pictogram.domain.repository.AuthRepository
import com.pictogram.domain.repository.ImagesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun providesAuthRepository(authApi: AuthApi): AuthRepository {
        return AuthRepositoryImpl(authApi)
    }

    @Provides
    @ViewModelScoped
    fun providesImageRepository(pictogramApi: PictogramApi): ImagesRepository {
        return ImageRepositoryImpl(pictogramApi)
    }

}