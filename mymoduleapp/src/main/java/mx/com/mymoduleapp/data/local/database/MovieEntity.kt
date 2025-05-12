package mx.com.mymoduleapp.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad que representa una película almacenada localmente en la base de datos.
 *
 * @property id ID único de la película.
 * @property title Título de la película.
 * @property overview Descripción o sinopsis de la película.
 * @property posterUrl URL del póster de la película.
 * @property rating Calificación promedio de la película.
 * @property releaseDate Fecha de lanzamiento de la película.
 * @property category Categoría a la que pertenece la película (por ejemplo: "now_playing", "popular").
 * @property page Página desde la cual fue obtenida la película (para paginación).
 */
@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String,
    val rating: Double,
    val releaseDate: String,
    val category: String, // "now_playing" o "popular"
    val page: Int
)

/**
 * Entidad que representa un video (por ejemplo, tráiler) asociado a una película.
 *
 * @property movieId ID de la película a la que pertenece el video.
 * @property key Clave única del video en la plataforma (por ejemplo, ID de YouTube).
 * @property name Nombre del video (por ejemplo, "Official Trailer").
 * @property site Plataforma donde se aloja el video (por ejemplo, "YouTube").
 * @property type Tipo de video (por ejemplo, "Trailer", "Teaser").
 */
@Entity(tableName = "videos")
data class VideoEntity(
    @PrimaryKey val movieId: Int,
    val key: String,
    val name: String,
    val site: String,
    val type: String,
)
