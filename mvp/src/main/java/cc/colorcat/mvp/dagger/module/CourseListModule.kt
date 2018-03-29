package cc.colorcat.mvp.dagger.module

import cc.colorcat.mvp.contract.IList
import cc.colorcat.mvp.dagger.PerFragment
import cc.colorcat.mvp.entity.Course
import cc.colorcat.mvp.presenter.CourseListPresenter
import dagger.Module
import dagger.Provides

/**
 * Created by cxx on 2018/3/29.
 * xx.ch@outlook.com
 */
@PerFragment
@Module
class CourseListModule {
    @Provides
    fun providePresenter(): IList.Presenter<Course> = CourseListPresenter()
}