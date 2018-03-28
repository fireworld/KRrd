package cc.colorcat.mvp.api

import cc.colorcat.mvp.entity.Course
import cc.colorcat.mvp.entity.Result
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by cxx on 2018/1/31.
 * xx.ch@outlook.com
 */
interface GetCourseList {
    @GET("api/teacher")
    fun getCourses(@Query("type") type: Int, @Query("num") num: Int): Observable<Result<List<Course>>>
}
