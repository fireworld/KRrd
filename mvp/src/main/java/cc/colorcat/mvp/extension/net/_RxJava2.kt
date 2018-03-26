package cc.colorcat.mvp.extension.net

import cc.colorcat.mvp.entity.Result
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by cxx on 2018/3/26.
 * xx.ch@outlook.com
 */
fun <T> transform(observable: Observable<Result<T>>): Observable<T> {
    return observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                if (it.status != Result.STATUS_OK) {
                    throw ApiException(it.status, it.msg)
                } else {
                    it.data
                }
            }
}