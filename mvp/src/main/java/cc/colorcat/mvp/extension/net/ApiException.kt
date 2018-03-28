package cc.colorcat.mvp.extension.net

/**
 * Created by cxx on 2018/3/26.
 * xx.ch@outlook.com
 */
class ApiException(val code: Int, val msg: String) : Exception(msg) {

    override fun toString(): String {
        return "ApiException(code=$code, msg='$msg')"
    }
}
