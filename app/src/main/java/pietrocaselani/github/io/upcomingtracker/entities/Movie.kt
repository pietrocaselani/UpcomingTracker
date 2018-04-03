package pietrocaselani.github.io.upcomingtracker.entities

data class Movie(
        val id: Int,
        val title: String,
        val overview: String,
        val posterLink: String?,
        val backdropLink: String?,
        val releaseDate: String?,
        val voteCount: Int,
        val voteAverage: Float
)