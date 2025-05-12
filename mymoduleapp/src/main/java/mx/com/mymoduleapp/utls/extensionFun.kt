package mx.com.mymoduleapp.utls

import mx.com.mymoduleapp.data.local.database.MovieEntity
import mx.com.mymoduleapp.data.local.database.VideoEntity
import mx.com.mymoduleapp.data.remote.model.MovieDto
import mx.com.mymoduleapp.data.remote.model.VideoDto
import mx.com.mymoduleapp.domain.model.Movie
import mx.com.mymoduleapp.domain.model.Video

/**
 * Convierte un [MovieDto] en una [MovieEntity] para su almacenamiento local.
 *
 * @param category Categoría de la película (ej. "popular", "now_playing").
 * @param page Página de la que se obtuvo esta película.
 * @return [MovieEntity] correspondiente al DTO.
 */
fun MovieDto.toMovieEntity(category: String, page: Int): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        overview = overview,
        posterUrl = "https://image.tmdb.org/t/p/w500$posterPath",
        rating = rating,
        releaseDate = releaseDate,
        category = category,
        page = page
    )
}

/**
 * Convierte un [MovieDto] en un objeto de dominio [Movie] para usar en la UI.
 *
 * @param page Página de la que se obtuvo esta película.
 * @return [Movie] convertido desde el DTO.
 */
fun MovieDto.toMovie(page: Int): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterUrl = "https://image.tmdb.org/t/p/w500$posterPath",
        rating = rating,
        releaseDate = releaseDate,
        page = page
    )
}

/**
 * Convierte una [MovieEntity] almacenada localmente en un objeto de dominio [Movie].
 *
 * @return [Movie] correspondiente a la entidad almacenada.
 */
fun MovieEntity.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterUrl = posterUrl,
        rating = rating,
        releaseDate = releaseDate,
        page = page
    )
}

/**
 * Convierte un [VideoDto] en un objeto de dominio [Video].
 *
 * @param movieId ID de la película asociada al video.
 * @return [Video] para uso en la lógica de UI.
 */
fun VideoDto.toVideo(movieId: Int) = Video(
    movieId = movieId,
    key = key,
    name = name,
    site = site
)

/**
 * Convierte un [VideoDto] en una [VideoEntity] para almacenamiento local.
 *
 * @param movieId ID de la película asociada al video.
 * @return [VideoEntity] representando el video.
 */
fun VideoDto.toVideoEntity(movieId: Int) = VideoEntity(
    movieId = movieId,
    key = key,
    name = name,
    site = site,
    type = type
)

/**
 * Convierte una [VideoEntity] almacenada localmente en un objeto de dominio [Video].
 *
 * @return [Video] correspondiente a la entidad almacenada.
 */
fun VideoEntity.toVideo() = Video(
    movieId = movieId,
    key = key,
    name = name,
    site = site
)