package cc.colorcat.mvp.dagger.module

import cc.colorcat.mvp.contract.ICourses
import cc.colorcat.mvp.dagger.PerActivity
import cc.colorcat.mvp.presenter.CoursesPresenter
import dagger.Module
import dagger.Provides

/**
 * Created by cxx on 2018/3/29.
 * xx.ch@outlook.com
 */
@PerActivity
@Module
class CoursesModule {
    @Provides
    fun providePresenter(): ICourses.Presenter = CoursesPresenter()
}
