package mx.com.mymoduleapp.data.remote.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mx.com.mymoduleapp.utls.NativeKeys
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Módulo de red que proporciona las dependencias necesarias para realizar peticiones HTTP
 * usando Retrofit, OkHttp y Gson.
 *
 * Este módulo está instalado en el [SingletonComponent], lo que significa que sus dependencias
 * tendrán una única instancia durante todo el ciclo de vida de la aplicación.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Proporciona una instancia singleton de [Gson] para la serialización y deserialización de JSON.
     *
     * @return una instancia de [Gson].
     */
    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    /**
     * Proporciona una instancia singleton de [OkHttpClient] que incluye un interceptor de autenticación.
     *
     * @return una instancia configurada de [OkHttpClient].
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .build()

    /**
     * Proporciona una instancia singleton de [Retrofit] configurada con una base URL,
     * un convertidor Gson y el cliente HTTP.
     *
     * @param gson instancia de [Gson] para el convertidor.
     * @param okHttpClient cliente HTTP con interceptor de autenticación.
     * @return una instancia de [Retrofit].
     */
    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(NativeKeys.getTmdbBaseUrl())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

    /**
     * Proporciona una implementación de [TMDbApiService] creada por Retrofit.
     *
     * @param retrofit instancia configurada de [Retrofit].
     * @return una implementación de [TMDbApiService].
     */
    @Provides
    @Singleton
    fun provideTMDbApiService(retrofit: Retrofit): TMDbApiService =
        retrofit.create(TMDbApiService::class.java)
}
