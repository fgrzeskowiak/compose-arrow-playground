package com.filippo.either.movies.data.remote

import com.filippo.either.movies.data.remote.models.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {
    @GET("3/movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): MoviesResponse
}
