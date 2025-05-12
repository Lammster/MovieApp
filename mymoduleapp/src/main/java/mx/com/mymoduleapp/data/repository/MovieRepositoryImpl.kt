package mx.com.mymoduleapp.data.repository

import mx.com.mymoduleapp.data.local.database.MovieDao
import mx.com.mymoduleapp.data.local.database.VideoDao
import mx.com.mymoduleapp.data.remote.api.TMDbApiService
import mx.com.mymoduleapp.domain.model.Movie
import mx.com.mymoduleapp.domain.model.Video
import mx.com.mymoduleapp.domain.repository.MovieRepository
import mx.com.mymoduleapp.utls.toMovie
import mx.com.mymoduleapp.utls.toMovieEntity
import mx.com.mymoduleapp.utls.toVideo
import mx.com.mymoduleapp.utls.toVideoEntity
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementación del repositorio de películas que se encarga de manejar las operaciones de
 * obtención de películas de la API y de la base de datos local.
 *
 * @param dao Repositorio de acceso a la base de datos local de películas.
 * @param videoDao Repositorio de acceso a la base de datos local de videos.
 * @param apiService Servicio de la API de TMDb para obtener películas y videos.
 */
@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val dao: MovieDao,
    private val videoDao: VideoDao,
    private val apiService: TMDbApiService
) : MovieRepository {

    /**
     * Obtiene las películas en reproducción actual desde la API o desde la base de datos local
     * si no se puede obtener de la API.
     *
     * @param page Número de página para la paginación de las películas.
     * @return Lista de películas.
     */
    override suspend fun getNowPlayingMovies(page: Int): List<Movie> {
        return try {
            val response = apiService.getNowPlayingMovies(page)
            if (response.isSuccessful) {
                val movies = response.body()?.movies ?: emptyList()
                val entities = movies.map { it.toMovieEntity("now_playing", page) }
                dao.insertMovies(entities)
                movies.map { it.toMovie(page) }
            } else {
                dao.getMoviesByCategoryAndPage("now_playing",page).map { it.toMovie() }
            }
        } catch (e: Exception) {
            dao.getMoviesByCategoryAndPage("now_playing",page).map { it.toMovie() }
        }
    }

    /**
     * Obtiene las películas populares desde la API o desde la base de datos local
     * si no se puede obtener de la API.
     *
     * @param page Número de página para la paginación de las películas.
     * @return Lista de películas populares.
     */
    override suspend fun getPopularMovies(page: Int): List<Movie> {
        return try {
            val response = apiService.getPopularMovies(page)
            if (response.isSuccessful) {
                val movies = response.body()?.movies ?: emptyList()
                val entities = movies.map { it.toMovieEntity("popular",page) }
                dao.insertMovies(entities)
                movies.map { it.toMovie(page) }
            } else {
                dao.getMoviesByCategoryAndPage("popular",page).map { it.toMovie() }
            }
        } catch (e: Exception) {
            dao.getMoviesByCategoryAndPage("popular", page).map { it.toMovie() }
        }
    }

    /**
     * Obtiene los videos asociados a una película específica desde la API o desde la base de datos local
     * si no se puede obtener de la API.
     *
     * @param movieId Identificador único de la película.
     * @return Lista de videos asociados a la película.
     */
    override suspend fun getMovieVideos(movieId: Int): List<Video> {
        return try {
            val response = apiService.getMovieVideos(movieId)
            if (response.isSuccessful) {
                val remoteVideos = response.body()?.videos ?: emptyList()
                val videoEntities = remoteVideos.map { it.toVideoEntity(movieId) }
                if (videoEntities.isNotEmpty()) {
                    videoDao.insertVideos(videoEntities)
                }
                remoteVideos.map { it.toVideo(movieId) }
            } else {
                videoDao.getVideosByMovieId(movieId).map { it.toVideo() }
            }
        } catch (e: Exception) {
            videoDao.getVideosByMovieId(movieId).map { it.toVideo() }
        }
    }

    /**
     * Obtiene los detalles de una película específica desde la base de datos local.
     *
     * @param movieId Identificador único de la película.
     * @return Detalles de la película.
     * @throws Exception Si no se encuentra la película en la base de datos.
     */
    override suspend fun getMovieDetails(movieId: Int): Movie {
        return dao.getMovieById(movieId)?.toMovie()
            ?: throw Exception("Película no encontrada en la base de datos")
    }
}

