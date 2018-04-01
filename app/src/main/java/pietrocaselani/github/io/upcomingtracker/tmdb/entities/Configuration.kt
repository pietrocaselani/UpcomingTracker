package pietrocaselani.github.io.upcomingtracker.tmdb.entities

import com.squareup.moshi.Json

data class Configuration(
        @Json(name = "images") val imageConfiguration: Images
)