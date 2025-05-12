package mx.com.mymoduleapp.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Representa la respuesta de la API de TMDb para una lista de películas.
 *
 * @property movies Lista de películas obtenidas en la respuesta.
 * @property page Página actual de los resultados.
 * @property totalPages Número total de páginas disponibles.
 */
data class MovieResponse(
    @SerializedName("results") val movies: List<MovieDto>,
    @SerializedName("page") val page: Int,
    @SerializedName("total_pages") val totalPages: Int
)

/**
 * Representa un objeto película recibido desde la API de TMDb.
 *
 * @property id Identificador único de la película.
 * @property title Título de la película.
 * @property overview Descripción o sinopsis de la película.
 * @property posterPath Ruta de la imagen del póster (relativa al servidor de imágenes de TMDb).
 * @property rating Promedio de votos (rating) de la película.
 * @property releaseDate Fecha de lanzamiento de la película.
 */
data class MovieDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("vote_average") val rating: Double,
    @SerializedName("release_date") val releaseDate: String
)
