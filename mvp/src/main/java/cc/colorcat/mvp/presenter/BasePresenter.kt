package cc.colorcat.mvp.presenter

import android.support.annotation.CallSuper
import cc.colorcat.mvp.ClientHelper
import cc.colorcat.mvp.api.ApiFactory
import cc.colorcat.mvp.contract.IBase
import cc.colorcat.mvp.entity.Result
import cc.colorcat.mvp.extension.SPHelper
import cc.colorcat.mvp.extension.json.JsonWrapper
import cc.colorcat.mvp.extension.net.ApiListener
import cc.colorcat.mvp.extension.net.ApiMapper
import cc.colorcat.mvp.extension.net.RxCallback
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by cxx on 18-1-31.
 * xx.ch@outlook.com
 */
abstract class BasePresenter<V : IBase.View> : IBase.Presenter<V> {
    private lateinit var disposables: CompositeDisposable
    protected val apiFactory: ApiFactory by lazy { ClientHelper.globalComponent.apiFactory() }
    protected val jsonWrapper: JsonWrapper by lazy { ClientHelper.globalComponent.jsonWrapper() }
    protected val spHelper: SPHelper by lazy { ClientHelper.globalComponent.spHelper() }
    protected var mView: V? = null

    @CallSuper
    override fun onCreate(view: V) {
        mView = view
    }

    @CallSuper
    override fun onDestroy() {
        if (this::disposables.isInitialized) {
            disposables.clear()
        }
        mView = null
    }

    protected fun <T> enqueue(observable: Observable<Result<T>>, create: () -> ApiListener<T>?) {
        enqueue(observable, create())
    }

    protected fun <T> enqueue(observable: Observable<Result<T>>, listener: ApiListener<T>?) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ApiMapper())
                .subscribeWith(RxCallback(listener))
                .also {
                    if (!this::disposables.isInitialized) disposables = CompositeDisposable()
                    disposables.add(it)
                }
    }
}
