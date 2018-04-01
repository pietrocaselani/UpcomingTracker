package pietrocaselani.github.io.upcomingtracker.tmdb.entities

import com.squareup.moshi.Json

data class Images(
        @Json(name = "secure_base_url") val secureBaseURL: String,
        @Json(name = "backdrop_sizes") val backdropSizes: List<String>,
        @Json(name = "poster_sizes") val posterSizes: List<String>
)