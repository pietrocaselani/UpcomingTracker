package pietrocaselani.github.io.upcomingtracker.upcoming

import pietrocaselani.github.io.upcomingtracker.entities.Movie

sealed class UpcomingMoviesViewState {
    class Loading: UpcomingMoviesViewState()
    data class Available(val movies: List<Movie>) : UpcomingMoviesViewState()
    class Unavailable : UpcomingMoviesViewState()
    data class Error(val message: String) : UpcomingMoviesViewState()
}
