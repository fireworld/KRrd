package cc.colorcat.mvp.extension.net

import cc.colorcat.mvp.entity.Result
import cc.colorcat.mvp.extension.L
import com.google.gson.JsonParseException
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.IOException

/**
 * Created by cxx on 2018/3/26.
 * xx.ch@outlook.com
 */
fun <T> transform(observable: Observable<Result<T>>, listener: ApiListener<T>?) {
    observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                when {
                    it.status != Result.STATUS_OK -> throw ApiException(it.status, it.msg, null)
                    it.data == null -> throw ApiException(it.status, it.msg, IllegalResultException("status is ok, but the data in Result is null"))
                    else -> it.data
                }
            }
            .subscribeWith(object : Observer<T> {
                override fun onSubscribe(d: Disposable) {
                    L.d("onSubscribe")
                    listener?.onStart()
                }

                override fun onNext(t: T) {
                    L.d("onNext")
                    listener?.onSuccess(t)
                }

                override fun onError(e: Throwable) {
                    L.e("onError")
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
                        is IOException -> {
                            code = Result.STATUS_CONNECT_ERROR
                            msg = Result.MSG_CONNECT_ERROR
                        }
                        else -> {
                            L.e(e)
                        }
                    }
                    listener?.onFailure(code, msg)
                }

                override fun onComplete() {
                    L.d("onComplete")
                    listener?.onFinish()
                }
            })
}

fun <T> transform(observable: Observable<Result<T>>, create: () -> ApiListener<T>?) {
    transform(observable, create())
}
