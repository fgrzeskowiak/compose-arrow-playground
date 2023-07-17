package com.filippo.either.movies.presentation

import arrow.core.Either
import arrow.core.getOrElse
import com.filippo.either.common.domain.RequestError
import com.filippo.either.common.presentation.errorMessage
import com.filippo.either.movies.domain.Movie

fun Either<RequestError, List<Movie>>.toUiState(sessionId: String?): MoviesUiState =
    MoviesUiState(
        error = leftOrNull()?.errorMessage,
        movies = getOrElse { emptyList() }.map {
            MoviesUiState.Movie(
                title = it.title,
                releaseDate = it.releaseDate,
                description = it.description,
                posterUrl = it.posterUrl,
                showFavIcon = sessionId != null
            )
        }
    )
