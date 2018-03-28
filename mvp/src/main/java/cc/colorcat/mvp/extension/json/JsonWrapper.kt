package cc.colorcat.mvp.extension.json

import java.lang.reflect.Type

/**
 * Created by cxx on 18-3-28.
 * xx.ch@outlook.com
 */
interface JsonWrapper {
    fun toJson(obj: Any): String

//    fun <T> fromJson(json: String, clazz: Class<T>): T

    fun <T> fromJson(json: String, typeOfT: Type): T
}