package cc.colorcat.mvp.presenter

import cc.colorcat.mvp.api.GetCourseList
import cc.colorcat.mvp.contract.IList
import cc.colorcat.mvp.entity.Course
import cc.colorcat.mvp.extension.L
import cc.colorcat.mvp.extension.net.WeakListener

/**
 * Created by cxx on 2018/3/29.
 * xx.ch@outlook.com
 */
class CourseListPresenter : AbsListPresenter<Course>() {
    private val mService = apiFactory.create(GetCourseList::class.java)

    override fun realGetItems(item: Course?, loadMore: Boolean) {
        enqueue(mService.getCourses(4, 30)) {
            object : WeakListener<List<Course>, IList.View<Course>>(mView) {
                override fun onSuccess(view: IList.View<Course>, data: List<Course>) {
                    view.hideTip()
                    view.refreshItems(data)
                }

                override fun onFailure(view: IList.View<Course>, code: Int, msg: String) {
                    super.onFailure(view, code, msg)
                    view.showTip()
                    L.e("onFailure, code=$code, msg=$msg")
                }

                override fun onFinish(view: IList.View<Course>) {
                    super.onFinish(view)
                    view.stopRefreshing()
                }
            }
        }
    }
}
