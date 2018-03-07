package cc.colorcat.mvp.module

import cc.colorcat.mvp.IClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by cxx on 2018/3/7.
 * xx.ch@outlook.com
 */
@Module
class ClientModule(private val client: IClient) {

    @Provides
    @Singleton
    fun provideClient(): IClient = client
}
