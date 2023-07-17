package com.filippo.either.movies.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.filippo.either.NavGraph
import com.filippo.either.R
import com.filippo.either.common.data.TextResource
import com.filippo.either.common.presentation.asString
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@Composable
@RootNavGraph(start = true)
@Destination
fun MoviesScreen(viewModel: MoviesViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val snackbarState = remember { SnackbarHostState() }
    val scaffoldState = rememberScaffoldState(snackbarHostState = snackbarState)
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isLoading,
        onRefresh = viewModel::fetchMovies
    )

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
        ) {
            state.error?.let { errorMessage ->
                RequestError(
                    error = errorMessage,
                    state = state,
                    snackbarState = snackbarState,
                    onRetry = viewModel::fetchMovies
                )
            }

            if (state.movies.isNotEmpty()) {
                Movies(
                    modifier = Modifier.padding(paddingValues),
                    movies = state.movies
                )
            }
            PullRefreshIndicator(
                modifier = Modifier.align(Alignment.TopCenter),
                refreshing = state.isLoading,
                state = pullRefreshState
            )
        }
    }
}

@Composable
private fun RequestError(
    error: TextResource,
    state: MoviesUiState,
    snackbarState: SnackbarHostState,
    onRetry: () -> Unit,
) {
    val errorMessage = error.asString()
    val retryLabel = stringResource(R.string.retry)
    LaunchedEffect(state) {
        val result = snackbarState.showSnackbar(
            message = errorMessage,
            duration = SnackbarDuration.Indefinite,
            actionLabel = retryLabel
        )

        when (result) {
            SnackbarResult.ActionPerformed -> onRetry()
            SnackbarResult.Dismissed -> Unit
        }
    }
}

@Composable
private fun Movies(movies: List<MoviesUiState.Movie>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),

        ) {
        itemsIndexed(movies) { index, movie ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AsyncImage(model = movie.posterUrl, contentDescription = null)
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = movie.title, style = MaterialTheme.typography.body1
                        )
                        if (movie.showFavIcon) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
                        }
                    }
                    Text(text = movie.releaseDate, style = MaterialTheme.typography.body2)
                    Text(text = movie.description, style = MaterialTheme.typography.body2)
                }
            }
            if (index != movies.lastIndex) {
                Spacer(modifier = Modifier.height(8.dp))
                Divider()
            }
        }
    }
}
