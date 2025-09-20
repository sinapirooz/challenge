package ir.miare.androidcodechallenge.data.repositoryImpl.di

import ir.miare.androidcodechallenge.data.util.UtilModules
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.miare.androidcodechallenge.data.repositoryImpl.PlayerRepositoryImpl
import ir.miare.androidcodechallenge.domain.repository.PlayerRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindPlayerRepository(
        impl: PlayerRepositoryImpl
    ): PlayerRepository
}

