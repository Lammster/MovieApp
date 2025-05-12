package mx.com.mymoduleapp.utls

import mx.com.mymoduleapp.data.local.database.MovieDao
import mx.com.mymoduleapp.data.local.database.VideoDao
import mx.com.mymoduleapp.data.remote.api.TMDbApiService
import mx.com.mymoduleapp.data.repository.MovieRepositoryImpl
import mx.com.mymoduleapp.domain.repository.MovieRepository

// DependencyProvider.kt (en tu módulo)
object DependencyProvider {
    // Variables que deben ser inicializadas por la app principal
    lateinit var movieDao: MovieDao
    lateinit var movieViDao: VideoDao
    lateinit var apiService: TMDbApiService
    // Instancia del repositorio
    val movieRepository: MovieRepository by lazy {
        MovieRepositoryImpl(movieDao, movieViDao, apiService)
    }
}