package ir.miare.androidcodechallenge.data.util

import ir.miare.androidcodechallenge.data.util.errorHandler.ErrorHandler
import ir.miare.androidcodechallenge.data.util.errorHandler.ErrorHandlerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilModules {

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class ErrorHandleModule{
        @Binds
        @Singleton
        abstract fun bindErrorHandler(errorHandlerImpl: ErrorHandlerImpl): ErrorHandler
    }

    @Provides
    fun provideApiRequestWrapper(errorHandler: ErrorHandler) =
        RequestWrapper(errorHandler = errorHandler)


}