package cc.colorcat.mvp.api

/**
 * Created by cxx on 2018/3/17.
 * xx.ch@outlook.com
 */
interface ApiFactory {
    fun <T> create(clazz: Class<T>): T
}
