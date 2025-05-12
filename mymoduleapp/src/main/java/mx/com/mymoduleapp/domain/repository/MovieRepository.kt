package mx.com.mymoduleapp.domain.repository

import mx.com.mymoduleapp.domain.model.Movie
import mx.com.mymoduleapp.domain.model.Video

/**
 * Interfaz que define las operaciones de acceso a datos relacionados con películas.
 * Esta capa abstrae la fuente de datos (remota o local) y permite obtener información de películas y videos.
 */
interface MovieRepository {

    /**
     * Obtiene la lista de películas que están actualmente en cartelera (now playing) para una página específica.
     *
     * @param page Número de página a consultar.
     * @return Una lista de objetos [Movie].
     */
    suspend fun getNowPlayingMovies(page: Int): List<Movie>

    /**
     * Obtiene la lista de películas populares (popular) para una página específica.
     *
     * @param page Número de página a consultar.
     * @return Una lista de objetos [Movie].
     */
    suspend fun getPopularMovies(page: Int): List<Movie>

    /**
     * Obtiene los videos asociados a una película específica (trailers, clips, etc.).
     *
     * @param movieId Identificador único de la película.
     * @return Una lista de objetos [Video] relacionados con la película.
     */
    suspend fun getMovieVideos(movieId: Int): List<Video>

    /**
     * Obtiene los detalles de una película específica desde la base de datos local.
     *
     * @param movieId Identificador único de la película.
     * @return Un objeto [Movie] con los detalles de la película.
     * @throws Exception si la película no se encuentra en la base de datos local.
     */
    suspend fun getMovieDetails(movieId: Int): Movie
}