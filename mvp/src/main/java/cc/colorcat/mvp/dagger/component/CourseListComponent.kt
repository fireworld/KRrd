package cc.colorcat.mvp.dagger.component

import cc.colorcat.mvp.dagger.PerFragment
import cc.colorcat.mvp.dagger.module.CourseListModule
import cc.colorcat.mvp.view.CourseListFragment
import dagger.Component

/**
 * Created by cxx on 2018/3/29.
 * xx.ch@outlook.com
 */
@PerFragment
@Component(modules = [CourseListModule::class])
interface CourseListComponent {

    fun inject(view: CourseListFragment)
}