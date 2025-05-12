package mx.com.movieapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Clase de la aplicación que inicializa Hilt para la inyección de dependencias.
 *
 * Esta clase extiende de `Application` y está anotada con `@HiltAndroidApp`, lo que permite
 * a Hilt gestionar la inyección de dependencias a través de toda la aplicación.
 *
 * La anotación `@HiltAndroidApp` marca la clase como el punto de entrada para la inyección
 * de dependencias en la aplicación, lo que permite a Hilt generar los componentes necesarios
 * para que las dependencias sean proporcionadas automáticamente a las actividades, fragmentos,
 * servicios, etc.
 *
 * Se debe agregar esta clase en el archivo `AndroidManifest.xml` como la clase `application`
 * para que funcione correctamente:
 *
 * ```xml
 * <application
 *     android:name=".AppApplication"
 *     ... >
 *     ...
 * </application>
 * ```
 */
@HiltAndroidApp
class AppAplication: Application() {}