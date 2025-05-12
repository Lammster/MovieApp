package mx.com.mymoduleapp.data.remote.api

import mx.com.mymoduleapp.data.remote.model.MovieResponse
import mx.com.mymoduleapp.data.remote.model.VideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * [TMDbApiService] define las llamadas a la API de The Movie Database (TMDb)
 * utilizando Retrofit. Proporciona funciones para obtener películas en cartelera,
 * populares y videos asociados a una película específica.
 */
interface TMDbApiService {
    /**
     * Obtiene la lista de películas que están actualmente en cartelera.
     *
     * @param page Número de página para paginación de resultados.
     * @return Una [Response] que contiene un [MovieResponse] con la lista de películas.
     */
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int
    ): Response<MovieResponse>

    /**
     * Obtiene la lista de películas populares.
     *
     * @param page Número de página para paginación de resultados.
     * @return Una [Response] que contiene un [MovieResponse] con la lista de películas.
     */
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): Response<MovieResponse>

    /**
     * Obtiene los videos asociados a una película específica.
     *
     * @param movieId ID único de la película.
     * @return Una [Response] que contiene un [VideoResponse] con los videos disponibles.
     */
    @GET("movie/{movieId}/videos")
    suspend fun getMovieVideos(
        @Path("movieId") movieId: Int,
    ): Response<VideoResponse>
}