package mx.com.movieapp.usecase

import kotlinx.coroutines.test.runTest
import mx.com.mymoduleapp.domain.repository.MovieRepository
import mx.com.mymoduleapp.domain.usecase.GetNowPlayingMoviesUseCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import junit.framework.TestCase.assertEquals
import mx.com.mymoduleapp.domain.model.Movie
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class GetNowPlayingMoviesUseCaseTest {

    @Mock
    lateinit var repository: MovieRepository

    private lateinit var useCase: GetNowPlayingMoviesUseCase

    private val mockMovie = Movie(
        id = 1,
        title = "Test Movie",
        overview = "Test Overview",
        posterUrl = "https://test.com/poster.jpg",
        rating = 8.5,
        releaseDate = "2024-01-01",
        page = 1
    )

    @Before
    fun setup() {
        useCase = GetNowPlayingMoviesUseCase(repository)
    }

    @Test
    fun `invoke should call repository`() = runTest {
        // Configurar
        val expectedMovies = listOf(mockMovie)
        whenever(repository.getNowPlayingMovies(1)).thenReturn(expectedMovies)

        // Ejecutar
        val result = useCase(1)

        // Verificar
        verify(repository, times(1)).getNowPlayingMovies(1) // Verificación con Mockito
        assertEquals(expectedMovies, result)
    }
}