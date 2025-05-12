package mx.com.mymoduleapp.domain.usecase

import mx.com.mymoduleapp.domain.model.Movie
import mx.com.mymoduleapp.domain.repository.MovieRepository
import javax.inject.Inject

/**
 * Caso de uso para obtener las películas que están actualmente en cartelera (now playing).
 *
 * @property repository Repositorio de películas que proporciona acceso a los datos.
 */
class GetNowPlayingMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    /**
     * Ejecuta el caso de uso.
     *
     * @param page Número de página a consultar.
     * @return Lista de películas [Movie] en cartelera para la página especificada.
     */
    suspend operator fun invoke(page: Int): List<Movie> {
        return repository.getNowPlayingMovies(page)
    }
}

/**
 * Caso de uso para obtener las películas populares (popular).
 *
 * @property repository Repositorio de películas que proporciona acceso a los datos.
 */
class GetPopularMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    /**
     * Ejecuta el caso de uso.
     *
     * @param page Número de página a consultar.
     * @return Lista de películas [Movie] populares para la página especificada.
     */
    suspend operator fun invoke(page: Int): List<Movie> {
        return repository.getPopularMovies(page)
    }
}
