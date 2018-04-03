package pietrocaselani.github.io.upcomingtracker.upcoming

import io.reactivex.Single
import pietrocaselani.github.io.upcomingtracker.entities.Movie

interface UpcomingMoviesRepository {
    fun fetchMovies(page: Int): Single<List<Movie>>
}