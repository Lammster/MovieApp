package mx.com.mymoduleapp.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import mx.com.mymoduleapp.domain.state.MovieDetailState
import mx.com.mymoduleapp.presentation.components.YouTubeVideoPlayer
import mx.com.mymoduleapp.presentation.viewmodel.MovieDetailViewModel

/**
 * Composable que muestra la pantalla de detalle de una película.
 * Carga automáticamente los detalles de la película y su video cuando se proporciona un [movieId].
 *
 * La UI reacciona a los distintos estados del ViewModel: cargando, éxito o error.
 * Incluye:
 * - Título de la película
 * - Video principal (YouTube)
 * - Rating
 * - Fecha de lanzamiento
 * - Sinopsis
 *
 * @param movieId ID de la película para cargar los detalles.
 * @param viewModel ViewModel que gestiona la obtención de detalles y videos de la película (inyectado por Hilt).
 */
@Composable
fun MovieDetailScreen(movieId: Int, viewModel: MovieDetailViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState.collectAsState()
    val movie by viewModel.movieDetails.collectAsState()
    val videos by viewModel.videos.collectAsState()

    LaunchedEffect(movieId) {
        viewModel.loadMovieDetails(movieId)
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        when (uiState) {
            MovieDetailState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally).testTag("loadingIndicator"))
            }
            is MovieDetailState.Error -> {
                Text(
                    text = "Error: ${(uiState as MovieDetailState.Error).message}",
                    color = MaterialTheme.colorScheme.error
                )
            }
            MovieDetailState.Success -> {
                movie?.let { movie ->

                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(bottom = 8.dp).testTag("Test Movie")
                    )

                    YouTubeVideoPlayer(
                        videoKey = videos.firstOrNull()?.key,
                        imageUrl = movie.posterUrl,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f).testTag("videoPlayer")
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Rating: ${movie.rating}/10",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 4.dp).testTag("Rating: 8.0/10")
                    )

                    Text(
                        text = "Release Date: ${movie.releaseDate}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.testTag("Release Date: 2022-01-01")
                    )

                    Text(
                        text = movie.overview,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp).testTag("Test Overview")
                    )
                }
            }
        }
    }
}
