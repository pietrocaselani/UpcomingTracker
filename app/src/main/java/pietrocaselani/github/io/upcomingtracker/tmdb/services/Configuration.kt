package pietrocaselani.github.io.upcomingtracker.tmdb.services

import io.reactivex.Flowable
import pietrocaselani.github.io.upcomingtracker.tmdb.entities.Configuration
import retrofit2.http.GET

interface Configuration {
    @GET("configuration")
    fun configuration(): Flowable<Configuration>
}