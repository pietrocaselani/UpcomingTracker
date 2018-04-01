package pietrocaselani.github.io.upcomingtracker.tmdb.services

import io.reactivex.Flowable
import pietrocaselani.github.io.upcomingtracker.tmdb.entities.Movie
import pietrocaselani.github.io.upcomingtracker.tmdb.entities.PagedResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Movies {
    @GET("movie/upcoming")
    fun upcoming(@Query("page") page: Int): Flowable<PagedResponse<Movie>>
}