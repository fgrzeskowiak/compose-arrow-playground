package com.filippo.either.movies.domain

import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository,
) {
    suspend operator fun invoke() = repository.getMovies()
}

