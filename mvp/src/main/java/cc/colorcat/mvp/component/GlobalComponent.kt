package cc.colorcat.mvp.component

import cc.colorcat.mvp.api.ApiFactory
import cc.colorcat.mvp.extension.SPHelper
import cc.colorcat.mvp.extension.json.JsonWrapper
import cc.colorcat.mvp.module.GlobalModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by cxx on 2018/3/24.
 * xx.ch@outlook.com
 */
@Singleton
@Component(modules = [GlobalModule::class])
interface GlobalComponent {
    fun apiFactory(): ApiFactory

    fun jsonWrapper(): JsonWrapper

    fun spHelper(): SPHelper
}
