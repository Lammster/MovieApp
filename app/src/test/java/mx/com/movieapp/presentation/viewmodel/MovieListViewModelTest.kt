package mx.com.movieapp.presentation.viewmodel

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import mx.com.mymoduleapp.domain.model.Movie
import mx.com.mymoduleapp.domain.state.MovieListState
import mx.com.mymoduleapp.domain.usecase.GetNowPlayingMoviesUseCase
import mx.com.mymoduleapp.domain.usecase.GetPopularMoviesUseCase
import mx.com.mymoduleapp.presentation.viewmodel.MovieListViewModel
import mx.com.mymoduleapp.utls.MovieCategory
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieListViewModelTest {

    @Mock lateinit var getNowPlayingUseCase: GetNowPlayingMoviesUseCase
    @Mock lateinit var getPopularUseCase: GetPopularMoviesUseCase

    private lateinit var viewModel: MovieListViewModel

    private val testDispatcher = StandardTestDispatcher()

    private val mockMovie = Movie(
        id = 1,
        title = "The Dark Knight",
        overview = "Batman vs Joker",
        posterUrl = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
        rating = 9.0,
        releaseDate = "2008-07-18",
        page = 1
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MovieListViewModel(getNowPlayingUseCase, getPopularUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadMovies should update state to Success`() = runTest {
        // Configurar
        val mockMovies = listOf(mockMovie)
        whenever(getNowPlayingUseCase(1)).thenReturn(mockMovies)

        // Ejecutar
        viewModel.loadMovies(MovieCategory.NOW_PLAYING)
        testDispatcher.scheduler.advanceUntilIdle()

        // Verificar
        val state = viewModel.uiState.value
        assertTrue(state is MovieListState.Success)
        assertEquals(mockMovies, (state as MovieListState.Success).movies)
    }
}