package com.pictogram.di

import android.content.Context
import com.pictogram.utils.SharedPrefFunctions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    @Singleton
    @Provides
    fun providesUserFunctions(@ApplicationContext context: Context): SharedPrefFunctions {
        return SharedPrefFunctions(context)
    }
}