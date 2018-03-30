package cc.colorcat.mvp.extension.net

import cc.colorcat.mvp.entity.Result
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/**
 * Created by cxx on 18-3-30.
 * xx.ch@outlook.com
 */
fun <T> call(observable: Observable<Result<T>>, create: () -> ApiListener<T>): DisposableObserver<T> {
    return call(observable, create())
}

fun <T> call(observable: Observable<Result<T>>, listener: ApiListener<T>): DisposableObserver<T> {
    return observable.subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map(ApiMapper())
            .subscribeWith(RxCallback(listener))
}
