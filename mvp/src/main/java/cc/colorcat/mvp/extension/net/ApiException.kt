package cc.colorcat.mvp.extension.net

/**
 * Created by cxx on 2018/3/26.
 * xx.ch@outlook.com
 */
class ApiException(val code: Int, val msg: String) : RuntimeException(msg) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ApiException

        if (code != other.code) return false
        if (msg != other.msg) return false

        return true
    }

    override fun hashCode(): Int {
        var result = code
        result = 31 * result + msg.hashCode()
        return result
    }

    override fun toString(): String {
        return "ApiException(code=$code, msg='$msg')"
    }
}
