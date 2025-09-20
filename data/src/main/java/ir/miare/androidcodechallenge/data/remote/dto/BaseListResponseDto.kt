package ir.miare.androidcodechallenge.data.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class BaseListResponseDto<out T>(
    @SerializedName("results") val result: List<T>,
    @SerializedName("totalPage") val totalPages: Int? = 1,
    @SerializedName("page") val page: Int? = 1,
)