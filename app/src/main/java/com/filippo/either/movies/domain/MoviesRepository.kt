package com.filippo.either.movies.domain

import com.filippo.either.common.domain.ApiResponse

interface MoviesRepository {
    suspend fun getMovies(): ApiResponse<List<Movie>>
}
