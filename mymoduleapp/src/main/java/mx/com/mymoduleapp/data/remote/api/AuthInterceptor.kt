package mx.com.mymoduleapp.data.remote.api

import mx.com.mymoduleapp.utls.NativeKeys
import okhttp3.Interceptor
import okhttp3.Response

/**
 * [AuthInterceptor] es un interceptor de OkHttp que agrega el encabezado de autorización
 * a todas las solicitudes HTTP realizadas por la aplicación.
 *
 * Utiliza un token de acceso Bearer obtenido de código nativo (NDK) a través de [NativeKeys.getTmdbToken],
 * lo cual proporciona mayor seguridad al evitar exponer el token directamente en el código fuente.
 */
class AuthInterceptor : Interceptor {

    /**
     * Intercepta la solicitud HTTP y agrega el encabezado "Authorization" con el token Bearer.
     *
     * @param chain la cadena de interceptores.
     * @return la respuesta HTTP resultante después de agregar el encabezado.
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(
                "Authorization",
                "Bearer ${NativeKeys.getTmdbToken()}"
            )
            .build()
        return chain.proceed(request)
    }
}