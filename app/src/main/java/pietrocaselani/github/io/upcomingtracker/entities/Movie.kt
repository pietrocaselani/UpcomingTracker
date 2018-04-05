package pietrocaselani.github.io.upcomingtracker.entities

import java.util.Date

data class Movie(
        val id: Int,
        val title: String,
        val overview: String,
        val posterLink: String?,
        val backdropLink: String?,
        val releaseDate: Date?,
        val voteCount: Int,
        val voteAverage: Float
)