package ir.miare.androidcodechallenge.data.util.errorHandler

import android.util.MalformedJsonException
import ir.miare.androidcodechallenge.domain.base.DataResult
import retrofit2.HttpException
import java.io.IOException
import java.io.InterruptedIOException
import java.net.SocketException
import java.net.SocketTimeoutException
import javax.inject.Inject


class ErrorHandlerImpl @Inject constructor() : ErrorHandler {

    override fun handleError(throwable: Throwable): DataResult.Error {
        return when (throwable) {
            is SocketTimeoutException, is IOException, is SocketException, is InterruptedIOException -> {
                DataResult.Error.InternalError
            }

            is MalformedJsonException -> {
                DataResult.Error.InvalidJsonResponse
            }

            is HttpException -> {
                DataResult.Error.NetworkError
            }

            else -> {
                DataResult.Error.UnknownError
            }
        }
    }
}