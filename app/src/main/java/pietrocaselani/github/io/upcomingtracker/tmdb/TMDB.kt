package pietrocaselani.github.io.upcomingtracker.tmdb

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import pietrocaselani.github.io.upcomingtracker.tmdb.services.Configuration
import pietrocaselani.github.io.upcomingtracker.tmdb.services.Movies
import retrofit2.Retrofit

class TMDB private constructor(builder: TMDB.Builder) {
    companion object {
        val API_HOST = "api.themoviedb.org"
        val API_VERSION = "3"
        val API_URL = "https://$API_HOST/$API_VERSION/"
    }

    private val retrofit: Retrofit

    init {
        val apiKey = builder.apiKey
        val client = builder.client.newBuilder().addInterceptor(TMDBAPIKeyInterceptor(apiKey)).build()

        retrofit = Retrofit.Builder().baseUrl(API_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    fun configuration(): Configuration {
        return retrofit.create(Configuration::class.java)
    }

    fun movies(): Movies {
        return retrofit.create(Movies::class.java)
    }

    class Builder {
        internal lateinit var client: OkHttpClient
        internal lateinit var apiKey: String

        fun client(client: OkHttpClient): Builder {
            this.client = client
            return this
        }

        fun apiKey(apiKey: String): Builder {
            this.apiKey = apiKey
            return this
        }

        fun build(): TMDB {
            return TMDB(builder = this)
        }
    }
}