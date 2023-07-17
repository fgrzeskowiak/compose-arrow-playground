package com.filippo.either.di

import android.content.Context
import android.util.Base64
import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.moczul.ok2curl.CurlInterceptor
import com.filippo.either.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import java.nio.charset.StandardCharsets
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val cacheSize = 10 * 10 * 1024L

    @Provides
    @Singleton
    fun cache(@ApplicationContext context: Context): Cache {
        val cachePath = File(context.cacheDir, "http-cache")
        return Cache(cachePath, cacheSize)
    }

    @Provides
    @Singleton
    fun authInterceptor(): Interceptor = Interceptor { chain ->
        val accessToken = Base64.decode(BuildConfig.MOVIES_ACCESS_TOKEN, Base64.DEFAULT)
            .toString(StandardCharsets.UTF_8)
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()
        chain.proceed(request)
    }

    @Provides
    @Singleton
    fun okHttpClient(cache: Cache, authInterceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .certificatePinner(CertificatePinner.Builder().add("").build())
            .addInterceptor(authInterceptor)
            .addInterceptor(CurlInterceptor { Log.d("Ok2Curl", it) })
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .cache(cache)
            .build()

    @Provides
    @Singleton
    fun jsonConverterFactory(): Converter.Factory =
        configuredJson.asConverterFactory("application/json".toMediaType())

    @Provides
    @Singleton
    fun retrofitBuilder(client: OkHttpClient, converterFactory: Converter.Factory): Retrofit =
        Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.MOVIES_BASE_URL)
            .addConverterFactory(converterFactory)
            .build()
}

val configuredJson = Json {
    ignoreUnknownKeys = true
    isLenient = true
}
