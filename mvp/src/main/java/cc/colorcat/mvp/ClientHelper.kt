package cc.colorcat.mvp

import android.util.Log
import cc.colorcat.mvp.dagger.component.DaggerGlobalComponent
import cc.colorcat.mvp.dagger.component.GlobalComponent
import cc.colorcat.mvp.dagger.module.GlobalModule
import cc.colorcat.mvp.extension.L
import cc.colorcat.mvp.extension.image.ImageLoader

/**
 * Created by cxx on 2018/3/1.
 * xx.ch@outlook.com
 */
object ClientHelper {
    lateinit var client: IClient
        private set

    val globalComponent: GlobalComponent by lazy {
        DaggerGlobalComponent.builder()
                .globalModule(GlobalModule(client.context, client.baseUrl, client.debug))
                .build()
    }

    fun init(client: IClient) {
        this.client = client
        L.Threshold = if (client.debug) Log.VERBOSE else 1000
        ImageLoader.init(client)
    }

    fun onLowMemory() {
        ImageLoader.releaseMemory()
    }
}
