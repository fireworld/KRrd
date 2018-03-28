package cc.colorcat.mvp.module

import cc.colorcat.mvp.IClient
import cc.colorcat.mvp.api.ApiFactory
import cc.colorcat.mvp.extension.L
import cc.colorcat.mvp.extension.json.gson
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by cxx on 2018/3/17.
 * xx.ch@outlook.com
 */
@Module
class NetworkModule(private val client: IClient) {
    @Provides
    @Singleton
    fun provideApiFactory(): ApiFactory {
        L.d("provideApiFactory calling...")
        return object : ApiFactory {
            private val retrofit: Retrofit by lazy {
                val cacheDir = client.context.cacheDir
                val cacheSize = Math.min((cacheDir.usableSpace * 0.2).toLong(), 30L * 1024L * 1024L)
                val cache = Cache(cacheDir, cacheSize)

                val okClient = OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor())
                        .cache(cache)
                        .build()
                Retrofit.Builder()
                        .client(okClient)
                        .baseUrl(client.baseUrl)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()
            }

            override fun <T> create(clazz: Class<T>): T {
                L.i("apiFactory.create calling...")
                return retrofit.create(clazz)
            }
        }
    }
}