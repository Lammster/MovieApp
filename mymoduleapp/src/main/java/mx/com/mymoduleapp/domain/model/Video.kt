package mx.com.mymoduleapp.domain.model

/**
 * Representa un video relacionado con una película.
 * Esta clase se utiliza para contener la información de los videos (como trailers o clips)
 * asociados a una película.
 *
 * @param movieId El identificador de la película a la que pertenece el video.
 * @param key La clave única del video, utilizada para acceder al video en la plataforma (por ejemplo, YouTube).
 * @param name El nombre del video, generalmente es el título del trailer o clip.
 * @param site El sitio web donde se encuentra el video (por ejemplo, "YouTube").
 */
data class Video(
    val movieId: Int,
    val key: String,
    val name: String,
    val site: String
)
