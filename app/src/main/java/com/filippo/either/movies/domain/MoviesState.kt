package com.filippo.either.movies.domain

import com.filippo.either.common.domain.RequestError

sealed interface MoviesState {
    object Loading: MoviesState
    data class Success(val movies: List<Movie>): MoviesState
    data class Failure(val error: RequestError): MoviesState
}
