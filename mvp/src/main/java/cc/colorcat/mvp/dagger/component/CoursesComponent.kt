package cc.colorcat.mvp.dagger.component

import cc.colorcat.mvp.dagger.PerActivity
import cc.colorcat.mvp.dagger.module.CoursesModule
import cc.colorcat.mvp.view.CoursesActivity
import dagger.Component

/**
 * Created by cxx on 2018/3/29.
 * xx.ch@outlook.com
 */
@PerActivity
@Component(modules = [CoursesModule::class])
interface CoursesComponent {

    fun inject(view: CoursesActivity)
}