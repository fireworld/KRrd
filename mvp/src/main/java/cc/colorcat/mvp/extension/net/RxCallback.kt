package cc.colorcat.mvp.extension.net

import cc.colorcat.mvp.entity.Result
import cc.colorcat.mvp.extension.L
import com.google.gson.JsonParseException
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by cxx on 2018/3/28.
 * xx.ch@outlook.com
 */
class RxCallback<T>(private val listener: ApiListener<T>?) : DisposableObserver<T>() {
    override fun onStart() {
        super.onStart()
        listener?.onStart()
    }

    override fun onNext(t: T) {
        listener?.onSuccess(t)
    }

    override fun onError(e: Throwable) {
        var code = Result.STATUS_UNKNOWN
        var msg = Result.MSG_UNKNOWN
        when (e) {
            is ApiException -> {
                code = e.code
                msg = e.msg
            }
            is JsonParseException -> {
                code = Result.STATUS_JSON_ERROR
                msg = "${Result.MSG_JSON_ERROR}, cause=${e.message}"
            }
            is HttpException -> {
                code = Result.STATUS_CONNECT_ERROR
                msg = "${Result.MSG_CONNECT_ERROR}, responseCode=${e.code()} responseMsg=${e.message()} cause=${e.message}"
            }
            is IOException -> {
                code = Result.STATUS_CONNECT_ERROR
                msg = "${Result.MSG_CONNECT_ERROR}, cause=${e.message}"
            }
        }
        listener?.onFailure(code, msg)
        listener?.onFinish()
        L.e(e)
    }

    override fun onComplete() {
        listener?.onFinish()
    }
}
