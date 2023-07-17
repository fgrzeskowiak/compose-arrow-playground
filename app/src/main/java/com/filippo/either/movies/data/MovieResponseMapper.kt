package com.filippo.either.movies.data

import com.filippo.either.movies.data.remote.models.MoviesResponse
import com.filippo.either.movies.domain.Movie

fun MoviesResponse.toModel() = movies.map { it.toModel() }

private fun MoviesResponse.Movie.toModel(): Movie = Movie(
    title = title,
    releaseDate = releaseDate,
    description = overview,
    posterUrl = "$IMAGE_REQUEST_BASE_URL$posterPath"
)

private const val IMAGE_REQUEST_BASE_URL = "https://image.tmdb.org/t/p/w185"
