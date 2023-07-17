package com.filippo.either.movies.presentation

import com.filippo.either.common.data.TextResource

data class MoviesUiState(
    val isLoading: Boolean = false,
    val error: TextResource? = null,
    val movies: List<Movie> = emptyList()
) {
    data class Movie(
        val title: String,
        val releaseDate: String,
        val description: String,
        val posterUrl: String,
        val showFavIcon: Boolean,
    )
}

data class MoviesUiState2(
    val isLoading: Boolean = false,
    val error: TextResource? = null,
    val movies: List<Movie> = emptyList(),
    val onRefresh: () -> Unit
) {
    data class Movie(
        val title: String,
        val releaseDate: String,
        val description: String,
        val posterUrl: String,
        val showFavIcon: Boolean,
    )
}
