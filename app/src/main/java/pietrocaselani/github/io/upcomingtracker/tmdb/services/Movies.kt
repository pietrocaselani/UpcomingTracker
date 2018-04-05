package pietrocaselani.github.io.upcomingtracker.tmdb.services

import io.reactivex.Single
import okhttp3.ResponseBody
import pietrocaselani.github.io.upcomingtracker.tmdb.entities.Movie
import pietrocaselani.github.io.upcomingtracker.tmdb.entities.PagedResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Movies {
    @GET("movie/upcoming")
    fun upcoming(@Query("page") page: Int): Single<ResponseBody>

    @GET("movie/{movieId}")
    fun details(@Path("movieId") movieId: Int): Single<ResponseBody>
}