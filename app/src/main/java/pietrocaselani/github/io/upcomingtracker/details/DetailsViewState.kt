package pietrocaselani.github.io.upcomingtracker.details

import pietrocaselani.github.io.upcomingtracker.entities.MovieDetails

sealed class DetailsViewState {
    object Loading: DetailsViewState()
    data class Available(val movieDetails: MovieDetails) : DetailsViewState()
    data class Error(val message: String) : DetailsViewState()
}