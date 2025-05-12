package mx.com.movieapp.presentation.screen

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.runner.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.MutableStateFlow
import mx.com.mymoduleapp.domain.model.Movie
import mx.com.mymoduleapp.domain.model.Video
import mx.com.mymoduleapp.domain.state.MovieDetailState
import mx.com.mymoduleapp.presentation.screen.MovieDetailScreen

import mx.com.mymoduleapp.presentation.viewmodel.MovieDetailViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config

@Config(sdk = [33])
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MovieDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Mock
    lateinit var viewModel: MovieDetailViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        // Mock de las funciones del ViewModel
        Mockito.`when`(viewModel.uiState).thenReturn(MutableStateFlow(MovieDetailState.Loading))
        Mockito.`when`(viewModel.movieDetails).thenReturn(MutableStateFlow(Movie(1, "Test Movie", "Test Overview", "2022-01-01", 0.0, "8.0",1)))
        Mockito.`when`(viewModel.videos).thenReturn(MutableStateFlow(listOf(Video(1,"videoKey", "https://video.url", "s"))))
    }

    @Test
    fun testLoadingState() {
        // Configuramos la UI para mostrar el estado de carga
        composeTestRule.setContent {
            MovieDetailScreen(movieId = 1, viewModel = viewModel)
        }

        // Verificamos que el CircularProgressIndicator esté visible
        composeTestRule.onNodeWithTag("loadingIndicator").assertIsDisplayed()
    }

    @Test
    fun testSuccessState() {
        val mockMovie = Movie(
            id = 1,
            title = "Test Movie",
            posterUrl = "https://example.com/poster.jpg",
            rating = 8.0,
            releaseDate = "2022-01-01",
            overview = "Test Overview",
            page = 1
        )

        val mockVideos = listOf(Video(movieId = 1, key = "testVideoKey", name = "", site = ""))

        // Mock para el estado de éxito
        Mockito.`when`(viewModel.uiState).thenReturn(MutableStateFlow(MovieDetailState.Success))
        Mockito.`when`(viewModel.movieDetails).thenReturn(MutableStateFlow(mockMovie))
        Mockito.`when`(viewModel.videos).thenReturn(MutableStateFlow(mockVideos))
        // Configuramos la UI para mostrar el estado de éxito
        composeTestRule.setContent {
            MovieDetailScreen(movieId = 1, viewModel = viewModel)
        }

        // Verificamos que el título de la película esté visible
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Test Movie").assertIsDisplayed()
        composeTestRule.onNodeWithTag("videoPlayer").assertIsDisplayed()
        composeTestRule.onNodeWithText("Rating: 8.0/10").assertIsDisplayed()
        composeTestRule.onNodeWithText("Release Date: 2022-01-01").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Overview").assertIsDisplayed()
    }

    @Test
    fun testErrorState() {
        // Mock para el estado de error
        val errorState = MovieDetailState.Error("Error loading movie details")
        Mockito.`when`(viewModel.uiState).thenReturn(MutableStateFlow(errorState))

        // Configuramos la UI para mostrar el estado de error
        composeTestRule.setContent {
            MovieDetailScreen(movieId = 1, viewModel = viewModel)
        }

        // Verificamos que el mensaje de error esté visible
        composeTestRule.onNodeWithText("Error: Error loading movie details").assertIsDisplayed()
    }
}