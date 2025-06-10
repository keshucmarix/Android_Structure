package com.app.di.module

import com.app.data.api.ApiHelper
import com.app.data.api.ApiService
import com.app.data.repository.AuthRepository
import com.app.data.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object DataRepositoryModule {

    @Provides
    fun provideDataRepositoryAuth(apiService: ApiService): AuthRepository {
        return AuthRepository(apiService)
    }

    @Provides
    fun provideDataRepositoryHome(apiService: ApiHelper): HomeRepository {
        return HomeRepository(apiService)
    }

}