package pietrocaselani.github.io.upcomingtracker.upcoming

import pietrocaselani.github.io.upcomingtracker.entities.Movie

sealed class UpcomingMoviesViewState {
    object Loading: UpcomingMoviesViewState()
    data class Available(val movies: List<Movie>) : UpcomingMoviesViewState()
    object Unavailable : UpcomingMoviesViewState()
    data class Error(val message: String) : UpcomingMoviesViewState()
}
