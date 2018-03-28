package cc.colorcat.mvp.extension.net

import cc.colorcat.mvp.entity.Result
import io.reactivex.functions.Function

/**
 * Created by cxx on 2018/3/28.
 * xx.ch@outlook.com
 */
class ApiMapper<T> : Function<Result<T>, T> {
    override fun apply(t: Result<T>): T {
        if (t.status != Result.STATUS_OK) {
            throw ApiException(t.status, t.msg)
        }
        if (t.data == null) {
            throw ApiException(Result.STATUS_NULL_DATA, Result.MSG_NULL_DATA)
        }
        return t.data
    }
}
