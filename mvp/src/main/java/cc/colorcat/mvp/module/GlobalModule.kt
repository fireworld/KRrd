package cc.colorcat.mvp.module

import android.content.Context
import android.preference.PreferenceManager
import cc.colorcat.mvp.api.ApiFactory
import cc.colorcat.mvp.extension.L
import cc.colorcat.mvp.extension.SPHelper
import cc.colorcat.mvp.extension.json.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.*
import javax.inject.Singleton

/**
 * Created by cxx on 18-3-28.
 * xx.ch@outlook.com
 */
@Module
class GlobalModule(
        private val context: Context,
        private val baseUrl: String,
        private val debug: Boolean
) {

    private val tag = GlobalModule::class.java.simpleName

    @Provides
    @Singleton
    fun provideGson(): Gson {
        L.e("provideGson calling...", tag)
        return GsonBuilder()
                .serializeNulls()
                .registerTypeAdapterFactory(NullStringAdapterFactory())
                .registerTypeAdapterFactory(NullMultiDateAdapterFactory("yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"))
                .registerTypeAdapterFactory(NullArrayTypeAdapterFactory())
                .registerTypeAdapterFactory(NullCollectionTypeAdapterFactory())
                .create()
    }

    @Provides
    @Singleton
    fun provideApiFactory(gson: Gson): ApiFactory {
        L.e("provideApiFactory calling...", tag)
        return object : ApiFactory {
            private val retrofit: Retrofit by lazy {
                val cacheDir = context.cacheDir
                val cacheSize = Math.min((cacheDir.usableSpace * 0.2).toLong(), 30L * 1024L * 1024L)
                val cache = Cache(cacheDir, cacheSize)

                val okClient = OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor())
                        .cache(cache)
                        .build()
                Retrofit.Builder()
                        .client(okClient)
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()
            }

            override fun <T> create(clazz: Class<T>): T {
                L.d("ApiFactory crate $clazz", tag)
                return retrofit.create(clazz)
            }
        }
    }

    @Provides
    @Singleton
    fun provideJsonWrapper(gson: Gson): JsonWrapper {
        L.e("provideJsonWrapper calling...", tag)
        return object : JsonWrapper {
            override fun toJson(obj: Any): String {
                L.d("JsonWrapper, toJson $obj", tag)
                return gson.toJson(obj)
            }

            override fun <T> fromJson(json: String, typeOfT: Type): T {
                L.d("JsonWrapper, fromJson, $json", tag)
                return gson.fromJson(json, typeOfT)
            }
        }
    }

    @Provides
    @Singleton
    fun provideSPHelper(jsonWrapper: JsonWrapper): SPHelper {
        L.e("provideSPHelper calling...", tag)
        return object : SPHelper {
            private val cache by lazy { WeakHashMap<String, Any>() }
            private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

            override fun set(key: String, value: Any) {
                L.d("SPHelper set, $key=$value", tag)
                cache[key] = value
                val json = jsonWrapper.toJson(value)
                val editor = sharedPreferences.edit()
                editor.putString(key, json)
                editor.apply()
            }

            override fun <T> get(key: String, typeOfT: Type): T? {
                L.d("SPHelper get, $key=$typeOfT", tag)
                @Suppress("UNCHECKED_CAST")
                var result: T? = cache[key] as? T
                if (result == null) {
                    val json = sharedPreferences.getString(key, "")
                    result = jsonWrapper.fromJson(json, typeOfT)
                    if (result != null) {
                        cache[key] = result
                    }
                }
                return result
            }

            override fun remove(key: String) {
                L.d("SPHelper remove, $key", tag)
                cache.remove(key)
                val editor = sharedPreferences.edit()
                editor.remove(key)
                editor.apply()
            }
        }
    }
}
