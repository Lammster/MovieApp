package mx.com.movieapp.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import mx.com.mymoduleapp.data.local.database.MovieDao
import mx.com.mymoduleapp.data.local.database.MovieEntity
import mx.com.mymoduleapp.data.local.database.VideoDao
import mx.com.mymoduleapp.data.remote.api.TMDbApiService
import mx.com.mymoduleapp.data.remote.model.MovieDto
import mx.com.mymoduleapp.data.repository.MovieRepositoryImpl
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryImplTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock lateinit var apiService: TMDbApiService
    @Mock lateinit var movieDao: MovieDao
    @Mock lateinit var videoDao: VideoDao

    private lateinit var repository: MovieRepositoryImpl

    @Before
    fun setup() {
        repository = MovieRepositoryImpl(movieDao, videoDao, apiService)
    }

    private val mockMovieEntity = MovieEntity(
        id = 1,
        title = "Test Movie",
        overview = "Test Overview",
        posterUrl = "https://test.com/poster.jpg",
        rating = 8.5,
        releaseDate = "2024-01-01",
        category = "now_playing",
        page = 1
    )

    private val mockMovieDto = MovieDto(
        id = 1,
        title = "Test Movie",
        overview = "Test Overview",
        posterPath = "/poster.jpg",
        rating = 8.5,
        releaseDate = "2024-01-01"
    )

    @Test
    fun `getNowPlayingMovies should return local data when API fails`() = runTest {
        // Configurar mocks
        val localMovies = listOf(mockMovieEntity)

        // Usar thenAnswer para excepciones en corrutinas
        whenever(apiService.getNowPlayingMovies(1)).thenAnswer {
            throw IOException("Simulated network error")
        }

        whenever(movieDao.getMoviesByCategoryAndPage("now_playing", page = 1)).thenReturn(localMovies)

        // Ejecutar
        val result = repository.getNowPlayingMovies(page = 1)

        // Verificar
        assertEquals(1, result.size)
        assertEquals("Test Movie", result[0].title) // Asegúrate que el título coincida con mockMovieEntity
    }
}