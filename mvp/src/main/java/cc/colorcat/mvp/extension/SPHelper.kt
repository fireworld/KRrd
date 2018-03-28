package cc.colorcat.mvp.extension

import java.lang.reflect.Type

/**
 * Created by cxx on 18-2-5.
 * xx.ch@outlook.com
 */
interface SPHelper {
    fun set(key: String, value: Any)

    fun <T> get(key: String, typeOfT: Type): T?

    fun remove(key: String)
}
