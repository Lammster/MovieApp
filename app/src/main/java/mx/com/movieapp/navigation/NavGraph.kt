package mx.com.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import mx.com.mymoduleapp.presentation.screen.ListViewMov
import mx.com.mymoduleapp.presentation.screen.MovieDetailScreen


/**
 * Composable que define el gráfico de navegación para la aplicación de películas.
 *
 * @param navController Controlador de navegación utilizado para manejar la navegación entre pantallas.
 * @param modifier Modificador opcional para personalizar el aspecto de la vista.
 *
 * Esta función define el flujo de navegación entre dos pantallas:
 * 1. **movieList**: Una lista de películas donde el usuario puede seleccionar una película para ver más detalles.
 * 2. **movieDetail/{movieId}**: Pantalla de detalles de una película, la cual recibe un `movieId` desde la pantalla anterior para mostrar información detallada sobre la película seleccionada.
 *
 * La navegación se maneja a través de un controlador de navegación (`NavHostController`), que permite mover al usuario entre las pantallas de la lista de películas y la vista de detalles.
 */
@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(navController, startDestination = "movieList",modifier = modifier) {
        // Pantalla de la lista de películas
        composable("movieList") {
            ListViewMov(onMovieClick = { movieId ->
                navController.navigate("movieDetail/$movieId")
            })
        }

        // Pantalla de detalle de la película
        composable("movieDetail/{movieId}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")?.toInt() ?: 0
            MovieDetailScreen(movieId = movieId)
        }
    }
}