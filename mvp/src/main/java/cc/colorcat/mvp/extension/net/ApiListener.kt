package cc.colorcat.mvp.extension.net

/**
 * Created by cxx on 18-3-28.
 * xx.ch@outlook.com
 */
interface ApiListener<R> {
    fun onStart()

    fun onSuccess(result: R)

    fun onFailure(code: Int, msg: String)

    fun onFinish()
}
