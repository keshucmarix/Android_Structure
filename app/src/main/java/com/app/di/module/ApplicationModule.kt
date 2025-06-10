package com.app.di.module

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.app.utils.AppPreferences
import com.app.utils.AppSession
import com.app.utils.Session

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import java.util.*
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {


    @Provides
    @Named("cache")
    internal fun provideCacheDir(application: Application): File {
        return application.cacheDir
    }

    @Provides
    @Singleton
    internal fun provideResources(application: Application): Resources {
        return application.resources
    }

    @Provides
    @Singleton
    internal fun provideCurrentLocale(resources: Resources): Locale {
        return resources.configuration.locales.get(0)
    }

    @Provides
    @Singleton
    internal fun provideApplicationContext(@ApplicationContext applicationContext: Context): Context {
        return applicationContext
    }

    @Provides
    @Singleton
    fun provideAppSession(
        appPreferences: AppPreferences,
        context: Context,
        apiKey: String
    ) = AppSession(appPreferences, context, apiKey)

    @Provides
    @Singleton
    internal fun provideSession(session: AppSession): Session = session
}