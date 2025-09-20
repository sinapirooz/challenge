package ir.miare.androidcodechallenge.data.remote.di

import android.content.Context
import android.util.Log
import ir.miare.androidcodechallenge.data.remote.configs.PlayerConfigs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.logicbase.mockfit.MockFitInterceptor
import ir.logicbase.mockfit.MockPathRule
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideMockFit(@ApplicationContext context: Context): MockFitInterceptor {
        return MockFitInterceptor(bodyFactory = {
            context.resources.assets.open(it)
        }, logger = { tag, message -> Log.d(tag, message) },
            baseUrl = PlayerConfigs.BASE_URL,
            mockFilesPath = "",
            mockFitEnable = true,
            apiEnableMock = true,
            apiIncludeIntoMock = arrayOf(),
            apiExcludeFromMock = arrayOf(),
            apiResponseLatency = 1000L,
            requestPathToMockPathRule = arrayOf(
                MockPathRule(
                    method = "GET",
                    route = "players",
                    responseFile = "allPlayers.json",
                    includeQueries = arrayOf("page=0"),
                    excludeQueries = arrayOf()
                ),
            )
        )
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(mockFitInterceptor: MockFitInterceptor): OkHttpClient {
        Log.d("MockFitInterceptor", mockFitInterceptor.toString())
        return   OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(mockFitInterceptor)
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()

    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(PlayerConfigs.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient).build()
    }

}