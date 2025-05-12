package mx.com.movieapp.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import mx.com.mymoduleapp.data.local.database.MovieDao
import mx.com.mymoduleapp.data.local.database.MovieDatabase
import org.junit.Before
import org.junit.runner.RunWith
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.runner.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import mx.com.mymoduleapp.data.local.database.MovieEntity
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.robolectric.annotation.Config

@Config(sdk = [33])
@RunWith(AndroidJUnit4::class)
class MovieDaoInstrumentedTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: MovieDatabase
    private lateinit var dao: MovieDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            MovieDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.movieDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndRetrieveMovies() = runBlocking {
        val movie = MovieEntity(
            id = 1,
            title = "Test Movie",
            overview = "Test Overview",
            posterUrl = "https://test.com/poster.jpg",
            rating = 8.5,
            releaseDate = "2024-01-01",
            category = "now_playing",
            page = 1
        )

        dao.insertMovies(listOf(movie))
        val result = dao.getMoviesByCategoryAndPage("now_playing", page = 1)

        assertEquals(1, result.size)
        assertEquals("Test Movie", result[0].title)
    }
}
