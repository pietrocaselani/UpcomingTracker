package pietrocaselani.github.io.upcomingtracker

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import pietrocaselani.github.io.upcomingtracker.upcoming.UpcomingMoviesViewModel
import pietrocaselani.github.io.upcomingtracker.upcoming.UpcomingStoreRepository

object ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return createUpcomingMoviesViewModel() as T
    }

    private fun createUpcomingMoviesViewModel(): UpcomingMoviesViewModel {
        val moviesStore = Environment.moviesStore
        val configurationStore = Environment.configurationStore
        val schedulers = Environment.schedulers

        val repository = UpcomingStoreRepository(moviesStore, configurationStore, schedulers)
        return UpcomingMoviesViewModel(repository, schedulers)
    }
}