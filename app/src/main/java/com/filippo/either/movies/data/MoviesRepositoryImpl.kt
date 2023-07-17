package com.filippo.either.movies.data

import com.filippo.either.common.domain.ApiResponse
import com.filippo.either.common.domain.apiCall
import com.filippo.either.movies.data.remote.MoviesApi
import com.filippo.either.movies.domain.Movie
import com.filippo.either.movies.domain.MoviesRepository
import javax.inject.Inject
import kotlinx.coroutines.delay

class MoviesRepositoryImpl @Inject constructor(private val api: MoviesApi) : MoviesRepository {

    override suspend fun getMovies(): ApiResponse<List<Movie>> =
        apiCall { api.getPopularMovies(1) }
            .map { response -> response.toModel() }
            .also { delay(1000) }
}
