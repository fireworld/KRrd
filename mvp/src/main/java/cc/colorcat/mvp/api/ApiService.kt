package cc.colorcat.mvp.api

import cc.colorcat.mvp.entity.Result
import cc.colorcat.mvp.entity.Course
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by cxx on 2018/3/16.
 * xx.ch@outlook.com
 */
interface ApiService {

    @GET
    fun getCourses(
            @Query("type") type: Int,
            @Query("num") number: Int
    ): Observable<Result<List<Course>>>
}