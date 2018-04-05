package pietrocaselani.github.io.upcomingtracker.details

import io.reactivex.Single
import pietrocaselani.github.io.upcomingtracker.entities.MovieDetails

interface DetailsRepository {
    fun fetchDetails(movieId: Int): Single<MovieDetails>
}