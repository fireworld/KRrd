package cc.colorcat.mvp.presenter

import cc.colorcat.mvp.api.GetCourseList
import cc.colorcat.mvp.contract.ICourses
import cc.colorcat.mvp.entity.Course
import cc.colorcat.mvp.extension.L
import cc.colorcat.mvp.extension.net.WeakListener

/**
 * Created by cxx on 18-1-31.
 * xx.ch@outlook.com
 */
class CoursesPresenter : BasePresenter<ICourses.View>(), ICourses.Presenter {
    private val mService = apiFactory.create(GetCourseList::class.java)

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
        enqueue(mService.getCourses(4, 30)) {
            object : WeakListener<List<Course>, ICourses.View>(mView) {
                override fun onStart(view: ICourses.View) {
                    super.onStart(view)
                    L.v("onStart from ICourses")
                }

                override fun onSuccess(view: ICourses.View, data: List<Course>) {
                    L.d("onSuccess from ICourses")
                    view.refreshCourses(data)
                }

                override fun onFailure(view: ICourses.View, code: Int, msg: String) {
                    super.onFailure(view, code, msg)
                    L.d("onFailure from ICourses, code=$code, msg=$msg")
                }

                override fun onFinish(view: ICourses.View) {
                    super.onFinish(view)
                    L.d("onFinish from ICourses")
                    view.stopRefresh()
                }
            }
        }
    }
}
