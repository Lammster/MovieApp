package mx.com.mymoduleapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.com.mymoduleapp.domain.model.Movie
import mx.com.mymoduleapp.domain.model.Video
import mx.com.mymoduleapp.domain.repository.MovieRepository
import mx.com.mymoduleapp.domain.state.MovieDetailState
import javax.inject.Inject


/**
 * ViewModel responsable de manejar la lógica de UI relacionada con los detalles de una película.
 * Se comunica con el [MovieRepository] para obtener los datos requeridos.
 *
 * Expone tres flujos de estado:
 * - [movieDetails]: Detalles individuales de una película.
 * - [videos]: Lista de videos relacionados con la película (trailers, clips, etc.).
 * - [uiState]: Estado de la UI que indica si está cargando, hubo un error o la carga fue exitosa.
 *
 * @param repository Repositorio de películas que proporciona acceso a los datos remotos o locales.
 */
@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movieDetails = MutableStateFlow<Movie?>(null)
    val movieDetails: StateFlow<Movie?> = _movieDetails

    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos: StateFlow<List<Video>> = _videos

    private val _uiState = MutableStateFlow<MovieDetailState>(MovieDetailState.Loading)
    val uiState: StateFlow<MovieDetailState> = _uiState

    /**
     * Carga los detalles de una película específica y sus videos asociados.
     * Actualiza los flujos de estado para que la UI reaccione adecuadamente.
     *
     * @param movieId ID de la película a cargar.
     */
    fun loadMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _uiState.value = MovieDetailState.Loading
            try {
                val movie = repository.getMovieDetails(movieId)
                _movieDetails.value = movie

                val videos = repository.getMovieVideos(movieId)
                _videos.value = videos

                _uiState.value = MovieDetailState.Success
            } catch (e: Exception) {
                _uiState.value = MovieDetailState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}

