package pietrocaselani.github.io.upcomingtracker.upcoming

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import pietrocaselani.github.io.upcomingtracker.Schedulers
import pietrocaselani.github.io.upcomingtracker.entities.Movie

class UpcomingMoviesViewModel(private val repository: UpcomingMoviesRepository,
                              private val schedulers: Schedulers) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val viewState = MutableLiveData<UpcomingMoviesViewState>()

    fun start() {
        val disposable = repository.fetchMovies(page = 1)
                .observeOn(schedulers.mainScheduler)
                .doOnSubscribe {
                    viewState.value = UpcomingMoviesViewState.Loading
                }
                .subscribe({
                    if (it.isEmpty()) {
                        viewState.value = UpcomingMoviesViewState.Unavailable
                    } else {
                        viewState.value = UpcomingMoviesViewState.Available(it)
                    }
                }, {
                    viewState.value = UpcomingMoviesViewState.Error(it.localizedMessage)
                })


        compositeDisposable.add(disposable)
    }

    fun select(movie: Movie) {
        viewState.postValue(UpcomingMoviesViewState.ShowDetails(movie))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}