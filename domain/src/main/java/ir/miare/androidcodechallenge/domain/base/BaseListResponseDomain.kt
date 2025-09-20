package ir.miare.androidcodechallenge.domain.base

data class BaseListResponseDomain<T>(
    val result: List<T>,
    val page: Int,
    val totalPages: Int,
    val totalResults: Int,
)
