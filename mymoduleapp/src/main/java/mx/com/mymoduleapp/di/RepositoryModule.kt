package mx.com.mymoduleapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mx.com.mymoduleapp.data.repository.MovieRepositoryImpl
import mx.com.mymoduleapp.domain.repository.MovieRepository

/**
 * Módulo de inyección de dependencias para los repositorios de la aplicación.
 * Este módulo proporciona la implementación concreta del repositorio de películas
 * y lo vincula con su interfaz correspondiente utilizando Dagger Hilt.
 *
 * @see MovieRepository para la interfaz que define las operaciones del repositorio de películas.
 * @see MovieRepositoryImpl para la implementación concreta del repositorio de películas.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * Vinca la implementación concreta del repositorio de películas con su interfaz correspondiente
     * usando Dagger Hilt para la inyección de dependencias.
     *
     * Esta función asegura que cada vez que se solicite una instancia de [MovieRepository],
     * se proporcionará una instancia de [MovieRepositoryImpl].
     *
     * @param impl La implementación concreta del repositorio de películas.
     * @return Una instancia de la interfaz [MovieRepository] que apunta a la implementación [MovieRepositoryImpl].
     */
    @Binds
    abstract fun bindMovieRepository(
        impl: MovieRepositoryImpl
    ): MovieRepository
}
