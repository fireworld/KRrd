package cc.colorcat.mvp.extension.net

import cc.colorcat.mvp.entity.Result
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by cxx on 18-3-30.
 * xx.ch@outlook.com
 */
class RxHelper<T> : ObservableTransformer<Result<T>, T> {
    override fun apply(upstream: Observable<Result<T>>): ObservableSource<T> {
        return upstream.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ApiMapper())
    }
}

//class RxHelper<T>(private val listener: ApiListener<T>) : ObservableConverter<Result<T>, DisposableObserver<T>> {
//
//    constructor(create: () -> ApiListener<T>) : this(create())
//
//    override fun apply(upstream: Observable<Result<T>>): DisposableObserver<T> {
//        return upstream.subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .map(ApiMapper())
//                .subscribeWith(RxCallback(listener))
//    }
//}
