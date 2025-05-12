package mx.com.mymoduleapp.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Representa la respuesta de la API de TMDb para una lista de videos de una película.
 *
 * @property videos Lista de videos obtenidos en la respuesta.
 */
data class VideoResponse(
    @SerializedName("results") val videos: List<VideoDto>
)

/**
 * Representa un objeto video asociado a una película desde la API de TMDb.
 *
 * @property id Identificador único del video.
 * @property key Clave única del video (utilizada para obtener el video desde el sitio correspondiente).
 * @property name Nombre o título del video.
 * @property site Sitio web donde se encuentra alojado el video (por ejemplo, YouTube).
 * @property type Tipo de video (por ejemplo, tráiler, clip, etc.).
 */
data class VideoDto(
    @SerializedName("id") val id: String,
    @SerializedName("key") val key: String,
    @SerializedName("name") val name: String,
    @SerializedName("site") val site: String,
    @SerializedName("type") val type: String,

)

