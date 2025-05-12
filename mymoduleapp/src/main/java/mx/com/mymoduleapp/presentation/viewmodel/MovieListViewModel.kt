package mx.com.mymoduleapp.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.com.mymoduleapp.domain.model.Movie
import mx.com.mymoduleapp.domain.state.MovieListState
import mx.com.mymoduleapp.domain.usecase.GetNowPlayingMoviesUseCase
import mx.com.mymoduleapp.domain.usecase.GetPopularMoviesUseCase
import mx.com.mymoduleapp.utls.MovieCategory
import javax.inject.Inject

/**
 * ViewModel encargado de manejar la lógica de negocio de la lista de películas.
 * Utiliza casos de uso para obtener películas "En cartelera" y "Populares".
 *
 * Mantiene el estado de la UI, control de paginación y el listado acumulado de películas.
 *
 * @param getNowPlayingUseCase Caso de uso para obtener películas en cartelera.
 * @param getPopularUseCase Caso de uso para obtener películas populares.
 */
@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getNowPlayingUseCase: GetNowPlayingMoviesUseCase,
    private val getPopularUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    private val _selectedCategory = MutableStateFlow<MovieCategory>(MovieCategory.NOW_PLAYING)
    val selectedCategory: StateFlow<MovieCategory> = _selectedCategory

    private val _uiState = MutableStateFlow<MovieListState>(MovieListState.Loading)
    val uiState: StateFlow<MovieListState> = _uiState

    var endReached by mutableStateOf(false)
        private set

    private val currentMovies = mutableListOf<Movie>()
    private var currentPage = 1
    private var isLoading = false

    /**
    * Establece la categoría de película seleccionada.
    *
    * Esta función actualiza el valor del estado [_selectedCategory] con la categoría proporcionada.
    * Usualmente se utiliza para actualizar la interfaz de usuario cuando el usuario selecciona una
    * nueva categoría (por ejemplo: populares, mejor valoradas, próximamente).
    *
    * @param category La nueva categoría de película que se desea seleccionar.
    */
    fun setSelectedCategory(category: MovieCategory) {
        _selectedCategory.value = category
    }

    /**
     * Carga las películas de acuerdo a la categoría seleccionada. Soporta paginación y reinicio de datos.
     *
     * @param category Categoría de películas a cargar (NOW_PLAYING o POPULAR).
     * @param reset Si es true, reinicia la lista y vuelve a cargar desde la página 1.
     */
    fun loadMovies(category: MovieCategory, reset: Boolean = false) {
        if (isLoading || endReached) return

        if (reset) {
            currentPage = 1
            endReached = false
            currentMovies.clear()
        }

        viewModelScope.launch {
            isLoading = true
            if (currentMovies.isEmpty()) {
                _uiState.value = MovieListState.Loading
            }

            try {
                val movies = when (category) {
                    MovieCategory.NOW_PLAYING -> {
                        getNowPlayingUseCase(currentPage)
                    }
                    MovieCategory.POPULAR -> {
                        getPopularUseCase(currentPage)
                    }
                }

                if (movies.isEmpty()) {
                    endReached = true
                } else {
                    currentMovies.addAll(movies)
                    _uiState.value = MovieListState.Success(currentMovies.toList())
                    currentPage++
                }
            } catch (e: Exception) {
                _uiState.value = MovieListState.Error(e.message ?: "Error")
            } finally {
                isLoading = false
            }
        }
    }
}

