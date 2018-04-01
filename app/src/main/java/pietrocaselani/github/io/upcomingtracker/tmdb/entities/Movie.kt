package pietrocaselani.github.io.upcomingtracker.tmdb.entities

import com.squareup.moshi.Json

data class Movie(
        val id: Int,
        val title: String,
        val overview: String,
        @Json(name = "poster_path") val posterPath: String?,
        @Json(name = "backdrop_path") val backdropPath: String?,
        @Json(name = "release_date") val releaseDate: String,
        @Json(name = "vote_count") val voteCount: Int,
        @Json(name = "vote_average") val voteAverage: Float
)