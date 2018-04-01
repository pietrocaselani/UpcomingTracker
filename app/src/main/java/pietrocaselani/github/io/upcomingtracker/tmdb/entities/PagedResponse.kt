package pietrocaselani.github.io.upcomingtracker.tmdb.entities

import com.squareup.moshi.Json

data class PagedResponse<out T>(
        val page: Int,
        @Json(name = "results") val results: List<T>,
        @Json(name = "total_pages") val totalPages: Int,
        @Json(name = "total_results") val totalResults: Int
)