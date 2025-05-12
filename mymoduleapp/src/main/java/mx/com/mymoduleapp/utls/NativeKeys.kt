package mx.com.mymoduleapp.utls

/**
 * Objeto encargado de acceder a claves sensibles a través de una biblioteca nativa (`native-lib`).
 *
 * Esta clase utiliza código nativo (C/C++) para ocultar valores sensibles como tokens de API.
 * El método `getTmdbToken` está implementado en la capa nativa y su valor no es visible en el código Java/Kotlin,
 * lo que añade una capa extra de seguridad contra ingeniería inversa.
 *
 * La biblioteca `native-lib` debe estar correctamente implementada y empacada en el proyecto.
 */
object NativeKeys {
    init {
        // Carga la biblioteca nativa al inicializar el objeto
        System.loadLibrary("native-lib")
    }

    /**
     * Obtiene el token de acceso de la API de TMDB desde la biblioteca nativa.
     *
     * @return Token como [String] extraído desde código nativo.
     */
    external fun getTmdbToken(): String

    /**
     * Obtiene la URL base de la API de TMDB desde la biblioteca nativa.
     *
     * @return URL base como [String] extraída desde código nativo.
     */
    external fun getTmdbBaseUrl(): String

    /**
     * Obtiene el nombre de la base de datos local usada para almacenar datos de TMDB,
     * desde la biblioteca nativa.
     *
     * @return Nombre de la base de datos como [String] extraído desde código nativo.
     */
    external fun getTmdbDataBaseName(): String
}