package com.filippo.either.login.di

import com.filippo.either.login.data.LoginRepositoryImpl
import com.filippo.either.login.data.remote.SessionApi
import com.filippo.either.login.domain.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(ViewModelComponent::class)
interface LoginModule {

    @Binds
    fun bindsLoginRepository(impl: LoginRepositoryImpl): LoginRepository

    companion object {
        @Provides
        fun provideSessionApi(retrofit: Retrofit): SessionApi = retrofit.create()
    }
}
