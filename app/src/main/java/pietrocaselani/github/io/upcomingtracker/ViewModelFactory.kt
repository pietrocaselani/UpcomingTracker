package pietrocaselani.github.io.upcomingtracker

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import pietrocaselani.github.io.upcomingtracker.details.DetailsStoreRepository
import pietrocaselani.github.io.upcomingtracker.details.DetailsViewModel
import pietrocaselani.github.io.upcomingtracker.entities.Movie
import pietrocaselani.github.io.upcomingtracker.upcoming.UpcomingMoviesViewModel
import pietrocaselani.github.io.upcomingtracker.upcoming.UpcomingStoreRepository

object ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return create(modelClass, emptyMap())
    }

    fun <T : ViewModel?> create(modelClass: Class<T>, params: Map<String, Any>): T {
        if (modelClass.isAssignableFrom(UpcomingMoviesViewModel::class.java))
            return createUpcomingMoviesViewModel() as T

        return createDetailsViewModel(params) as T
    }

    private fun createDetailsViewModel(params: Map<String, Any>): DetailsViewModel {
        val movie = params[Movie.PARCELABLE_KEY] as? Movie ?: throw RuntimeException("Can't create DetailsViewModel without a movie")

        val movieDetailsStore = Environment.movieDetailsStore
        val configurationStore = Environment.configurationStore
        val schedulers = Environment.schedulers

        val repository = DetailsStoreRepository(movieDetailsStore, configurationStore, schedulers)

        return DetailsViewModel(movie, repository, schedulers)
    }

    private fun createUpcomingMoviesViewModel(): UpcomingMoviesViewModel {
        val moviesStore = Environment.moviesStore
        val configurationStore = Environment.configurationStore
        val schedulers = Environment.schedulers

        val repository = UpcomingStoreRepository(moviesStore, configurationStore, schedulers)
        return UpcomingMoviesViewModel(repository, schedulers)
    }
}