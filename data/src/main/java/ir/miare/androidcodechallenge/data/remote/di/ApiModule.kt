package ir.miare.androidcodechallenge.data.remote.di

import ir.miare.androidcodechallenge.data.remote.api.PlayerApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun providePlayerApi(retrofit: Retrofit): PlayerApi =
        retrofit.create(PlayerApi::class.java)

}