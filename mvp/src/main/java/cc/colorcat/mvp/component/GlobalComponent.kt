package cc.colorcat.mvp.component

import cc.colorcat.mvp.api.ApiFactory
import cc.colorcat.mvp.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by cxx on 2018/3/24.
 * xx.ch@outlook.com
 */
@Singleton
@Component(modules = [NetworkModule::class])
interface GlobalComponent {

    fun apiFactory(): ApiFactory
}
