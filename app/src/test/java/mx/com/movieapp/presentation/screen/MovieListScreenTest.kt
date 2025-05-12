package mx.com.movieapp.presentation.screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.MutableStateFlow
import mx.com.movieapp.ui.theme.MovieAppTheme
import mx.com.mymoduleapp.domain.model.Movie
import mx.com.mymoduleapp.domain.state.MovieListState
import mx.com.mymoduleapp.presentation.screen.ListViewMov
import mx.com.mymoduleapp.presentation.viewmodel.MovieListViewModel
import mx.com.mymoduleapp.utls.MovieCategory
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import org.robolectric.annotation.Config


class TestActivity : ComponentActivity()

@Config(sdk = [33])
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MovieListScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private fun createMockViewModel(state: MovieListState): MovieListViewModel {
        val mockViewModel = mock<MovieListViewModel>()
        val uiStateFlow = MutableStateFlow(state)
        val categoryFlow = MutableStateFlow(MovieCategory.NOW_PLAYING)

        whenever(mockViewModel.uiState).thenReturn(uiStateFlow)
        whenever(mockViewModel.selectedCategory).thenReturn(categoryFlow)

        return mockViewModel
    }

    @Test
    fun showLoadingWhenStateIsLoading() {
        val mockViewModel = createMockViewModel(MovieListState.Loading)

        composeTestRule.setContent {
            MovieAppTheme {
                ListViewMov(onMovieClick = {}, viewModel = mockViewModel)
            }
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("loadingView").assertIsDisplayed()
    }

    @Test
    fun showErrorWhenStateIsError() {
        val mockViewModel = createMockViewModel(MovieListState.Error("Error de prueba"))

        composeTestRule.setContent {
            MovieAppTheme {
                ListViewMov(onMovieClick = {}, viewModel = mockViewModel)
            }
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("errorView").assertIsDisplayed()
    }

    @Test
    fun showMoviesWhenStateIsSuccess() {
        val mockMovies = listOf(
            Movie(
                id = 1,
                title = "Pelicula 1",
                overview = "Descripción 1",
                posterUrl = "",
                rating = 8.0,
                releaseDate = "2024-01-01",
                page = 1
            )
        )

        val mockViewModel = createMockViewModel(MovieListState.Success(mockMovies))
        (mockViewModel.selectedCategory as MutableStateFlow).value = MovieCategory.NOW_PLAYING

        composeTestRule.setContent {
            MovieAppTheme {
                ListViewMov(onMovieClick = {}, viewModel = mockViewModel)
            }
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Pelicula 1").assertIsDisplayed()
    }
}