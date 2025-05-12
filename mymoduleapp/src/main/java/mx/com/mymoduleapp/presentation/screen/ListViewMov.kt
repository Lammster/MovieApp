package mx.com.mymoduleapp.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import mx.com.mymoduleapp.domain.model.Movie
import mx.com.mymoduleapp.domain.state.MovieListState
import mx.com.mymoduleapp.presentation.viewmodel.MovieListViewModel
import mx.com.mymoduleapp.utls.MovieCategory

/**
 * Composable principal que muestra una lista de películas organizadas por categoría (como "Now Playing").
 * Permite cambiar de categoría mediante tabs y maneja estados de carga, éxito y error.
 *
 * @param onMovieClick Callback que se ejecuta cuando se selecciona una película. Retorna el ID de la película.
 * @param viewModel ViewModel inyectado con Hilt que gestiona la lógica de negocio y el estado de la UI.
 */
@Composable
fun ListViewMov(onMovieClick: (Int) -> Unit,
                viewModel: MovieListViewModel = hiltViewModel()) {

    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(selectedCategory) {
        viewModel.loadMovies(selectedCategory, reset = true)
    }

    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = selectedCategory.ordinal,
            modifier = Modifier.height(50.dp)
        ) {
            MovieCategory.entries.forEach { category ->
                Tab(
                    selected = selectedCategory == category,
                    onClick = { viewModel.setSelectedCategory(category) },
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = category.name.replace("_", " "),
                        modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center).padding(bottom = 5.dp)
                    )
                }
            }
        }

        when (val state = uiState) {
            is MovieListState.Loading -> Text("Cargando...",  modifier = Modifier.testTag("loadingView"))
            is MovieListState.Success -> MovieList(movies = state.movies,
                onMovieClick = onMovieClick,
                onLoadMore = { if (viewModel.endReached.not()) viewModel.loadMovies(selectedCategory) },
                listState,
                viewModel)
            is MovieListState.Error -> {
                Text("Error: ${state.message}",modifier = Modifier.testTag("errorView"))
            }
        }
    }
}

/**
 * Composable que renderiza una lista perezosa (LazyColumn) de películas.
 * Soporta paginación automática cuando el usuario se aproxima al final de la lista.
 *
 * @param movies Lista de películas a mostrar.
 * @param onMovieClick Callback que se ejecuta al hacer clic en una película.
 * @param onLoadMore Callback que se invoca al detectar que se debe cargar más contenido.
 * @param listState Estado de scroll de la lista, utilizado para mantener posición o detectar el fin.
 * @param viewModel ViewModel que contiene el flag de fin de resultados (`endReached`).
 */
@Composable
private fun MovieList(
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit,
    onLoadMore: () -> Unit,
    listState: LazyListState,
    viewModel: MovieListViewModel
) {

    LazyColumn (state = listState,
        modifier = Modifier.fillMaxSize()){
        itemsIndexed(movies) { index, movie ->
            MovieItem(movie = movie, onMovieClick = { onMovieClick(movie.id) })
            if (index >= movies.lastIndex - 3 && viewModel.endReached.not()) {
                onLoadMore()
            }
        }
    }
}

/**
 * Composable que representa visualmente un ítem de película en la lista.
 * Muestra la imagen del póster con loader y el título de la película.
 *
 * @param movie Objeto que contiene los datos de la película.
 * @param onMovieClick Callback ejecutado al hacer clic en el ítem.
 */
@Composable
private fun MovieItem(movie: Movie, onMovieClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .clickable { onMovieClick() }
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                val painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = movie.posterUrl)
                        .apply<ImageRequest.Builder>(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                        }).build()
                )

                val paintingState = painter.state
                if (paintingState is AsyncImagePainter.State.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(48.dp),
                        strokeWidth = 4.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Image(
                    painter = painter,
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )
            }

            Text(
                text = movie.title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .fillMaxWidth(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }
}
