package pietrocaselani.github.io.upcomingtracker.details

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import pietrocaselani.github.io.upcomingtracker.Schedulers
import pietrocaselani.github.io.upcomingtracker.entities.Movie

class DetailsViewModel(private val movie: Movie,
                       private val repository: DetailsRepository,
                       private val schedulers: Schedulers) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val viewState = MutableLiveData<DetailsViewState>()

    fun start() {
        val disposable = repository.fetchDetails(movie.id)
                .observeOn(schedulers.mainScheduler)
                .doOnSubscribe {
                    viewState.postValue(DetailsViewState.Loading)
                }.subscribe({
                    viewState.postValue(DetailsViewState.Available(it))
                }, {
                    viewState.postValue(DetailsViewState.Error(it.localizedMessage))
                })

        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }
}