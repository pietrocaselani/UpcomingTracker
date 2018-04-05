package pietrocaselani.github.io.upcomingtracker.tmdb.entities

data class Movie(
        val id: Int,
        val title: String,
        val overview: String,
        val poster_path: String?,
        val backdrop_path: String?,
        val release_date: String?,
        val vote_count: Int,
        val vote_average: Float,
        val genres: List<Genre>,
        val imdb_id: String?,
        val tagline: String?)