package mx.com.mymoduleapp.presentation.components

import android.content.Context
import android.net.ConnectivityManager
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

/**
 * Composable que muestra un reproductor de video de YouTube si hay conexión a internet
 * y un video disponible. Si no hay conexión o ocurre un error, se muestra una imagen
 * como fallback y un mensaje de error.
 *
 * @param videoKey Clave del video de YouTube.
 * @param imageUrl URL del poster de la película a mostrar si no se puede reproducir el video.
 * @param modifier Modificador opcional para aplicar al contenedor.
 */
@Composable
fun YouTubeVideoPlayer(
    videoKey: String?,
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val isConnected = connectivityManager.activeNetworkInfo?.isConnectedOrConnecting == true

    var showVideo by remember { mutableStateOf(isConnected && videoKey != null) }
    var loadingError by remember { mutableStateOf(false) }

    LaunchedEffect(isConnected) {
        if (!isConnected) {
            showVideo = false
        }
    }

    Box(modifier = modifier) {
        if (showVideo && !loadingError) {
            YouTubePlayerWrapper(
                videoKey = videoKey.orEmpty(),
                onError = { loadingError = true },
                modifier = Modifier.fillMaxSize()
            )
        } else {

            AsyncImage(
                model = imageUrl,
                contentDescription = "Poster de la película",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            if (!isConnected) {
                ErrorMessage("Sin conexión a internet")
            } else if (loadingError) {
                ErrorMessage("Error al cargar el video")
            }
        }
    }
}

/**
 * Composable que muestra un mensaje de error sobre un fondo oscuro translúcido.
 *
 * @param message Texto del mensaje a mostrar.
 */
@Composable
private fun ErrorMessage(message: String) {
    Text(
        text = message,
        color = Color.White,
        modifier = Modifier
            .padding(8.dp)
            .background(Color.Black.copy(alpha = 0.7f))
    )
}

/**
 * Composable que integra el reproductor de YouTube utilizando `YouTubePlayerView`.
 * Intenta cargar y reproducir el video especificado. En caso de error, invoca el callback `onError`.
 *
 * @param videoKey Clave del video de YouTube a cargar.
 * @param onError Callback a ejecutar si ocurre un error al cargar el video.
 * @param modifier Modificador opcional para aplicar al reproductor.
 */
@Composable
private fun YouTubePlayerWrapper(
    videoKey: String,
    onError: () -> Unit,
    modifier: Modifier = Modifier
) {
    var youTubePlayer: YouTubePlayer? by remember { mutableStateOf(null) }

    AndroidView(
        factory = { context ->
            YouTubePlayerView(context).apply {
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(player: YouTubePlayer) {
                        youTubePlayer = player
                        try {
                            player.loadVideo(videoKey, 0f)
                        } catch (e: Exception) {
                            onError()
                        }
                    }

                    override fun onError(player: YouTubePlayer, error: PlayerConstants.PlayerError) {
                        onError()
                    }
                })
            }
        },
        modifier = modifier,
        update = { view ->
            try {
                youTubePlayer?.loadVideo(videoKey, 0f)
            } catch (e: Exception) {
                onError()
            }
        }
    )
}
