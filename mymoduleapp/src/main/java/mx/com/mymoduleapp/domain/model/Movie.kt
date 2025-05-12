package mx.com.mymoduleapp.domain.model

/**
 * Representa una película en la aplicación.
 * Esta clase se utiliza para contener la información relevante de una película
 * que se obtiene de la API o de la base de datos y se utiliza en la capa de presentación.
 *
 * @param id El identificador único de la película.
 * @param title El título de la película.
 * @param overview Una descripción corta de la película (sinopsis).
 * @param posterUrl La URL de la imagen del póster de la película.
 * @param rating El puntaje promedio de la película, generalmente basado en calificaciones de usuarios.
 * @param releaseDate La fecha de estreno de la película en formato `yyyy-MM-dd`.
 * @param page El número de la página en la que se encuentra la película si se está utilizando paginación.
 */
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String,
    val rating: Double,
    val releaseDate: String,
    val page:Int
)