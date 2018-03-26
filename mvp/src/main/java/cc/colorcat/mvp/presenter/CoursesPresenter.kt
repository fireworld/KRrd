package cc.colorcat.mvp.presenter

import cc.colorcat.mvp.entity.Result
import cc.colorcat.mvp.ClientHelper
import cc.colorcat.mvp.api.GetCourseList
import cc.colorcat.mvp.api.GetCoursesImpl
import cc.colorcat.mvp.contract.ICourses
import cc.colorcat.mvp.entity.Course
import cc.colorcat.mvp.extension.L
import cc.colorcat.mvp.extension.net.transform
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by cxx on 18-1-31.
 * xx.ch@outlook.com
 */
class CoursesPresenter : BasePresenter<ICourses.View>(), ICourses.Presenter {
    private val mService = GetCoursesImpl()

    override fun onCreate(view: ICourses.View) {
        super.onCreate(view)
        doGetCourses()
    }

    override fun doGetCourses() {
        realGetCourses()
    }

    override fun toRefreshCourses() {
        realGetCourses()
    }

    private fun realGetCourses() {
//        mService.setType(4).setNumber(30).send {
//            object : WeakListener<List<Course>, ICourses.View>(mView) {
//                override fun onSuccess(view: ICourses.View, data: List<Course>) {
//                    view.refreshCourses(data)
//                }
//
//                override fun onFinish(view: ICourses.View) {
//                    view.stopRefresh()
//                }
//            }
//        }
        transform(ClientHelper.globalComponent.apiFactory().create(GetCourseList::class.java).getCourses(4, 30))
                .subscribeWith(object : Observer<List<Course>> {
                    override fun onSubscribe(d: Disposable) {
                        L.d("onSubscribe")
                    }

                    override fun onNext(t: List<Course>) {
                        L.v("onNext, $t")
                            mView?.refreshCourses(t)
                    }

                    override fun onError(e: Throwable) {
                        L.e("onError")
                        e.printStackTrace()
                    }

                    override fun onComplete() {
                        L.i("onComplete")
                    }
                })
//                .subscribe {
//                    if (it.status == 0 && it.msg == "ok") {
//                        mView?.refreshCourses(it.data!!)
//                    }
//                }
    }
}
