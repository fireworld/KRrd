package cc.colorcat.mvp.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by cxx on 18-1-31.
 * xx.ch@outlook.com
 */
data class Result<T>(
        @SerializedName("status") val status: Int,
        @SerializedName("msg") val msg: String,
        @SerializedName("data") val data: T?
) {
    companion object {
        const val STATUS_OK = 1

        const val STATUS_CONNECT_ERROR = -1
        const val MSG_CONNECT_ERROR = "connect error"

        const val STATUS_JSON_ERROR = -2
        const val MSG_JSON_ERROR = "json parser error"

        const val STATUS_UNKNOWN = -2
        const val MSG_UNKNOWN = "unknown"
    }
}
