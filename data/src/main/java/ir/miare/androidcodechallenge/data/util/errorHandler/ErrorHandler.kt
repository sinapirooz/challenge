package ir.miare.androidcodechallenge.data.util.errorHandler

import ir.miare.androidcodechallenge.domain.base.DataResult
import okhttp3.ResponseBody


interface ErrorHandler {
    fun handleError(throwable: Throwable): DataResult.Error
}