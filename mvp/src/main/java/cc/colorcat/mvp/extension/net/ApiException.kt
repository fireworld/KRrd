package cc.colorcat.mvp.extension.net

/**
 * Created by cxx on 2018/3/26.
 * xx.ch@outlook.com
 */
class ApiException(val code: Int, msg: String) : Exception(msg) {

}