package com.filippo.either.movies.di

import com.filippo.either.movies.data.MoviesRepositoryImpl
import com.filippo.either.movies.data.remote.MoviesApi
import com.filippo.either.movies.domain.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(ViewModelComponent::class)
interface MoviesModule {

    @Binds
    fun bindsRepository(impl: MoviesRepositoryImpl): MoviesRepository

    companion object {
        @Provides
        fun providesMoviesApi(retrofit: Retrofit): MoviesApi = retrofit.create()
    }
}
