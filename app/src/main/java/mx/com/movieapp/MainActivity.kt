package mx.com.mymovapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import mx.com.movieapp.navigation.NavGraph

/**
 * Actividad principal de la aplicación que inicializa la UI y gestiona la navegación.
 *
 * Esta actividad está anotada con `@AndroidEntryPoint`, lo que permite la inyección de dependencias
 * mediante Hilt en sus componentes, como el `NavController`, y otras dependencias que puedan ser
 * necesarias dentro de esta actividad.
 *
 * En esta actividad, se establece un `Scaffold` con un `TopAppBar` que contiene el título "Movie App".
 * La navegación entre pantallas se gestiona mediante un `NavGraph` que se configura en el cuerpo de la actividad.
 *
 * **Funciones principales**:
 * - Inicialización de la UI mediante `Scaffold`.
 * - Configuración de la barra superior (TopAppBar) con color de fondo y contenido.
 * - Navegación entre las pantallas a través de un `NavHostController`.
 *
 * **Uso de Hilt**:
 * - La anotación `@AndroidEntryPoint` permite la inyección de dependencias en esta actividad.
 *
 * **Dependencias**:
 * - `NavGraph`: Se utiliza para gestionar la navegación entre pantallas.
 * - `TopAppBar`: Se usa para mostrar una barra superior con el título de la aplicación.
 * - `Scaffold`: Contenedor de la UI principal de la actividad, que incluye la barra de aplicaciones y el contenido principal.
 *
 * **@OptIn(ExperimentalMaterial3Api::class)**:
 * - Esta anotación se usa para habilitar características experimentales del Material3, como el diseño de `Scaffold` y `TopAppBar`.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Habilita el diseño "Edge-to-Edge", permitiendo que el contenido ocupe toda la pantalla.
        enableEdgeToEdge()

        setContent {
            Scaffold(
                topBar = {
                    // Barra superior con título de la aplicación
                    TopAppBar(
                        title = { Text("Movie App") },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            ) { innerPadding ->
                // Configuración de la navegación, usando el NavController y el innerPadding para el contenido.
                NavGraph(
                    navController = rememberNavController(),
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}