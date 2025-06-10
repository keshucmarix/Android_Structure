package com.app.di.module

import android.content.Context
import android.util.Log
import com.app.R
import com.app.data.api.ApiService
import com.app.utils.AppPreferences
import com.app.utils.AppSession
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

    @Provides
    @Singleton
    fun provideAppPref(context: Context): AppPreferences {
        return AppPreferences(context)
    }

    @Provides
    @Singleton
    @BearerTokenQualifier
    fun provideBearerToken(appSession: AppSession): String {
        return appSession.bearerToken ?: ""
    }

    @Provides
    fun providesBaseUrl(context: Context): String {
        return context.getString(R.string.HOST)
    }

    @Provides
    fun providesHttpUrl(context: Context): HttpUrl {
        return HttpUrl.Builder()
            .scheme(context.getString(R.string.SCHEME))
            .host(context.getString(R.string.HOST))
            .addPathSegments(context.getString(R.string.PATH_SEGMENT))
            .port(context.getString(R.string.PORT).toInt())
            .build()
    }

    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        @BearerTokenQualifier bearerToken: String,
        session: AppSession,
        connectivityInterceptorImpl: ConnectivityInterceptorImpl
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            callTimeout(1, TimeUnit.MINUTES)
            connectTimeout(1, TimeUnit.MINUTES)
            readTimeout(1, TimeUnit.MINUTES)
            writeTimeout(1, TimeUnit.MINUTES)
            addInterceptor(UnauthorizedInterceptor)
         //   addInterceptor(EncryptionInterceptor("12345BCD67890ARF12345BCD67890ARF"))
            addInterceptor {
                val newRequest = it.request().newBuilder()
                    // .addHeader("Authorization", session.bearerToken.toString())
                    .addHeader("accept-language", session.appLang)
                    .build()
                it.proceed(newRequest)
            }
        }.addInterceptor(loggingInterceptor)
            .addInterceptor(connectivityInterceptorImpl)
            .build()

    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient,
        baseUrl: HttpUrl,
        converterFactory: Converter.Factory
    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    fun provideRestApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}

object UnauthorizedInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val response = chain.proceed(originalRequest)

        if (response.code == 401) {
            Log.e("TAG", "intercept: " + response.message)
        }

        return response
    }
}