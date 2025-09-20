package ir.miare.androidcodechallenge.data.local.di

import android.content.Context
import androidx.room.Room
import ir.miare.androidcodechallenge.data.local.PlayerDao
import ir.miare.androidcodechallenge.data.local.PlayerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlayerDatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context
    ): PlayerDatabase {
        return Room.databaseBuilder(
            appContext,
            PlayerDatabase::class.java,
            "Player.db"
        ).build()
    }

    @Provides
    fun providePlayerDao(database: PlayerDatabase): PlayerDao =
        database.playerDao()
}
