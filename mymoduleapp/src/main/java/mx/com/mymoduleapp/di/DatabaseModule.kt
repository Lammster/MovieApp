package mx.com.mymoduleapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mx.com.mymoduleapp.data.local.database.MovieDao
import mx.com.mymoduleapp.data.local.database.MovieDatabase
import mx.com.mymoduleapp.data.local.database.VideoDao
import mx.com.mymoduleapp.utls.NativeKeys
import javax.inject.Singleton

/**
 * Módulo de inyección de dependencias para proporcionar instancias relacionadas con la base de datos
 * utilizando Dagger Hilt. Este módulo proporciona la base de datos de películas y sus respectivos DAO.
 *
 * @see MovieDatabase para la definición de la base de datos de películas.
 * @see MovieDao para el acceso a la tabla de películas.
 * @see VideoDao para el acceso a la tabla de videos.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Proporciona una instancia de la base de datos de películas.
     *
     * Si la base de datos ya existe, se mantendrá; de lo contrario, se creará una nueva.
     * La migración destructiva se habilita para que si hay un cambio en la estructura de la base de datos,
     * la base de datos se destruya y se reconstruya.
     *
     * @param context El contexto de la aplicación, utilizado para crear la base de datos.
     * @return Una instancia de la base de datos de películas.
     */
    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            NativeKeys.getTmdbDataBaseName()
        ).fallbackToDestructiveMigration().build()
    }

    /**
     * Proporciona una instancia del DAO de películas, que es responsable de interactuar con la
     * tabla de películas en la base de datos.
     *
     * @param database La instancia de la base de datos de películas.
     * @return Una instancia de MovieDao.
     */
    @Provides
    fun provideMovieDao(database: MovieDatabase): MovieDao = database.movieDao()

    /**
     * Proporciona una instancia del DAO de videos, que es responsable de interactuar con la
     * tabla de videos en la base de datos.
     *
     * @param database La instancia de la base de datos de películas.
     * @return Una instancia de VideoDao.
     */
    @Provides
    fun provideVideoDao(database: MovieDatabase): VideoDao = database.videoDao()
}