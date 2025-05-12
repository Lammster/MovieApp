package mx.com.mymoduleapp.data.local.database

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase

/**
 * Data Access Object (DAO) para acceder a la base de datos de películas.
 */
@Dao
interface MovieDao {
    /**
     * Obtiene una lista de películas por categoría y página.
     *
     * @param category Categoría de la película (por ejemplo: "popular", "now_playing").
     * @param page Página correspondiente a los resultados paginados.
     * @return Lista de entidades [MovieEntity] correspondientes a la categoría y página.
     */
    @Query("SELECT * FROM movies WHERE category = :category AND page = :page")
    suspend fun getMoviesByCategoryAndPage(category: String, page: Int): List<MovieEntity>

    /**
     * Inserta una lista de películas en la base de datos.
     * Si existe una película con el mismo ID, será reemplazada.
     *
     * @param movies Lista de películas a insertar.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    /**
     * Obtiene una película por su ID.
     *
     * @param movieId ID de la película.
     * @return Entidad [MovieEntity] si existe, o null en caso contrario.
     */
    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun getMovieById(movieId: Int): MovieEntity?
}

/**
 * DAO para acceder a los videos relacionados con una película.
 */
@Dao
interface VideoDao {
    /**
     * Obtiene los videos asociados a un ID de película.
     *
     * @param movieId ID de la película.
     * @return Lista de entidades [VideoEntity] relacionadas con la película.
     */
    @Query("SELECT * FROM videos WHERE movieId = :movieId")
    suspend fun getVideosByMovieId(movieId: Int): List<VideoEntity>

    /**
     * Inserta una lista de videos en la base de datos.
     * Si existe un video con el mismo ID, será reemplazado.
     *
     * @param videos Lista de videos a insertar.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(videos: List<VideoEntity>)
}

/**
 * Base de datos de Room que contiene las entidades de películas y videos.
 */
@Database(
    entities = [MovieEntity::class, VideoEntity::class],
    version = 1,
)
abstract class MovieDatabase : RoomDatabase() {
    /**
     * Proporciona acceso al DAO de películas.
     */
    abstract fun movieDao(): MovieDao

    /**
     * Proporciona acceso al DAO de videos.
     */
    abstract fun videoDao(): VideoDao
}