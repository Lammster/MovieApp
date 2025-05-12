package mx.com.mymoduleapp.domain.state

/**
 * Representa el estado de la pantalla de detalle de película.
 */
sealed class MovieDetailState {
    object Loading : MovieDetailState()
    object Success : MovieDetailState()
    data class Error(val message: String) : MovieDetailState()
}