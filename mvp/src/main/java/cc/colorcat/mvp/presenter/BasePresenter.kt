package cc.colorcat.mvp.presenter

import android.support.annotation.CallSuper
import cc.colorcat.mvp.ClientHelper
import cc.colorcat.mvp.api.ApiFactory
import cc.colorcat.mvp.component.GlobalComponent
import cc.colorcat.mvp.contract.IBase

/**
 * Created by cxx on 18-1-31.
 * xx.ch@outlook.com
 */
abstract class BasePresenter<V : IBase.View> : IBase.Presenter<V>, ApiFactory {
    protected val mComponent: GlobalComponent = ClientHelper.globalComponent
    protected var mView: V? = null

    @CallSuper
    override fun onCreate(view: V) {
        mView = view
    }

    @CallSuper
    override fun onDestroy() {
        mView = null
    }

    override fun <T> create(clazz: Class<T>): T = mComponent.apiFactory().create(clazz)
}
