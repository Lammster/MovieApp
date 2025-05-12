package mx.com.mymoduleapp.domain.state

import mx.com.mymoduleapp.domain.model.Movie

/**
 * Representa los diferentes estados de la pantalla de lista de películas.
 */
sealed class MovieListState {
    /** Estado de carga inicial o de más resultados. */
    object Loading : MovieListState()
    /**
     * Estado de éxito que contiene la lista de películas.
     *
     * @param movies Lista de películas cargadas.
     */
    data class Success(val movies: List<Movie>) : MovieListState()
    /**
     * Estado de error que contiene el mensaje de fallo.
     *
     * @param message Mensaje explicando el error.
     */
    data class Error(val message: String) : MovieListState()
}