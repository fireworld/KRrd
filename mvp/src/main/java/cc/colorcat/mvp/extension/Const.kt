package cc.colorcat.mvp.extension

/**
 * Created by cxx on 2018/3/1.
 * xx.ch@outlook.com
 */
class Const private constructor() {
    companion object {
        const val MAX_AGE_CACHE: Long = 5L * 60L * 1000L
    }

    class key private constructor() {
        companion object {
            const val container_layout = "container_layout"
            const val course_list = "course_list"
        }
    }

    class msg private constructor() {
        companion object {
        }
    }
}
