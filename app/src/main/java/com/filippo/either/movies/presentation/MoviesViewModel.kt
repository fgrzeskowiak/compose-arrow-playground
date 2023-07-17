package com.filippo.either.movies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filippo.either.common.domain.SessionProvider
import com.filippo.either.movies.domain.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.runningReduce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    sessionProvider: SessionProvider,
) : ViewModel() {

    private val refreshTrigger = MutableSharedFlow<Unit>(replay = 1)

    init {
        fetchMovies()
    }

    val state: StateFlow<MoviesUiState> = combine(
        refreshTrigger,
        sessionProvider.sessionId
    ) { _, sessionId -> sessionId }
        .flatMapLatest(::fetchMoviesUiState)
        .keepPreviouslyLoadedMoviesOnRefresh()
        .stateIn(viewModelScope, SharingStarted.Lazily, MoviesUiState())

    private fun fetchMoviesUiState(sessionId: String?) = flow {
        emit(MoviesUiState(isLoading = true))
        emit(getMoviesUseCase().toUiState(sessionId))
    }

    private fun Flow<MoviesUiState>.keepPreviouslyLoadedMoviesOnRefresh() =
        runningReduce { previous, new ->
            new.copy(movies = if (new.isLoading) previous.movies else new.movies)
        }

    fun fetchMovies() {
        viewModelScope.launch {
            refreshTrigger.emit(Unit)
        }
    }
}
