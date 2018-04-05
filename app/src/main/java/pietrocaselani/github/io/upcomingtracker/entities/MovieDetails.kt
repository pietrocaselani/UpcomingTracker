package pietrocaselani.github.io.upcomingtracker.entities

import pietrocaselani.github.io.upcomingtracker.tmdb.entities.Genre
import java.util.*

data class MovieDetails(val id: Int,
                        val title: String,
                        val overview: String,
                        val posterLink: String?,
                        val backdropLink: String?,
                        val releaseDate: Date?,
                        val voteCount: Int,
                        val voteAverage: Float,
                        val genres: List<Genre>,
                        val imdbId: String?,
                        val tagline: String?)