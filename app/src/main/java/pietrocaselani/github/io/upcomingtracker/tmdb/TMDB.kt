package pietrocaselani.github.io.upcomingtracker.tmdb

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class TMDB private constructor(builder: TMDB.Builder) {
    companion object {
        val API_HOST = "api.themoviedb.org"
        val API_VERSION = "3"
        val API_URL = "https://$API_HOST/$API_VERSION/"
    }

    private val retrofit: Retrofit

    init {
        val apiKey = builder.apiKey ?: throw IllegalArgumentException("apiKey can't be null")
        val client = builder.client ?: OkHttpClient.Builder().addInterceptor(TMDBAPIKeyInterceptor(apiKey)).build()

        val moshi = builder.moshi ?: Moshi.Builder().build()
        val moshiConverter = MoshiConverterFactory.create(moshi)

        retrofit = Retrofit.Builder().baseUrl(API_URL)
                .client(client)
                .addConverterFactory(moshiConverter)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    class Builder {
        var client: OkHttpClient? = null
        var apiKey: String? = null
        var moshi: Moshi? = null

        fun build() {
            TMDB(builder = this)
        }
    }
}