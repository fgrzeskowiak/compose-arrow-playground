package com.filippo.either.account.di

import com.filippo.either.account.data.AccountRepositoryImpl
import com.filippo.either.account.data.remote.AccountApi
import com.filippo.either.account.domain.AccountRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
interface AccountModule {

    @Binds
    fun bindsRepository(impl: AccountRepositoryImpl): AccountRepository

    companion object {
        @Provides
        fun provideAccountApi(retrofit: Retrofit): AccountApi = retrofit.create()
    }
}
