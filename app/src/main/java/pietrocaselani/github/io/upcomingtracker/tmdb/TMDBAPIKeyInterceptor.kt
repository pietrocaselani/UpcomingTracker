package pietrocaselani.github.io.upcomingtracker.tmdb

import okhttp3.Interceptor
import okhttp3.Response

internal class TMDBAPIKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        val request = chain?.request()

        if (TMDB.API_HOST != request?.url()?.host()) {
            return chain?.proceed(request!!)!!
        }

        val builder = request.newBuilder()

        val apiKeyURL = request.url().newBuilder().setQueryParameter("api_key", apiKey).build()

        builder.url(apiKeyURL)

        return chain.proceed(builder.build())
    }
}