package ir.miare.androidcodechallenge.domain.base


sealed class DataResult<out T> {

    data class Success<out T>(val data: T) : DataResult<T>()

    sealed class Error() : DataResult<Nothing>() {
        data object NetworkError : Error()

        data object ServerError : Error()

        data object InvalidJsonResponse : Error()

        data object InternalError : Error()

        data object UnknownError : Error()


    }

    data object Loading : DataResult<Nothing>()

}
