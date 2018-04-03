package pietrocaselani.github.io.upcomingtracker.tmdb.entities

import com.squareup.moshi.Json
import pietrocaselani.github.io.upcomingtracker.TMDBMovie

data class PagedResponse(
        val page: Int,
        @Json(name = "results") val results: List<TMDBMovie>,
        val total_pages: Int,
        val total_results: Int
)