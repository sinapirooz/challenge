package ir.miare.androidcodechallenge.data.util

import android.util.Log
import ir.miare.androidcodechallenge.data.util.errorHandler.ErrorHandler
import ir.miare.androidcodechallenge.domain.base.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class RequestWrapper @Inject constructor(private val errorHandler: ErrorHandler) {

    fun <T, V> apiWrapper(
        apiRequest: suspend () -> Response<T>?,
        mapper: (T) -> V,
        action: suspend (V) -> Unit = {},
    ): Flow<DataResult<V>> =
        flow {
            emit(DataResult.Loading)
            try {
                val response = apiRequest()
                if (response?.isSuccessful == true) {
                    val entity = mapper(response.body()!!)
                    action(entity)
                    emit(
                        DataResult.Success(
                            data = entity,
                        )
                    )
                } else {
                    throw HttpException(response)
                }
            } catch (ex: Throwable) {
                Log.d("MockFitInterceptor","wrapper ==> $ex")
                emit(errorHandler.handleError(throwable = ex))
            }
        }
            .flowOn(Dispatchers.IO)
}