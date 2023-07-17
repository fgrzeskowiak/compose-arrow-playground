package com.filippo.either.common.di

import com.filippo.either.common.data.DataStoreSessionManager
import com.filippo.either.common.domain.SessionProvider
import com.filippo.either.common.domain.SessionWriter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
interface CommonModule {

    @Binds
    fun bindsSessionProvider(impl: DataStoreSessionManager): SessionProvider

    @Binds
    fun bindsSessionWriter(impl: DataStoreSessionManager): SessionWriter

    companion object {
        @Provides
        @Singleton
        fun provideCoroutineScope(): CoroutineScope =
            CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }
}
