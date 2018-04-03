package pietrocaselani.github.io.upcomingtracker.tmdb.services

import io.reactivex.Single
import okhttp3.ResponseBody
import pietrocaselani.github.io.upcomingtracker.tmdb.entities.Configuration
import retrofit2.http.GET

interface Configuration {
    @GET("configuration")
    fun configuration(): Single<ResponseBody>
}