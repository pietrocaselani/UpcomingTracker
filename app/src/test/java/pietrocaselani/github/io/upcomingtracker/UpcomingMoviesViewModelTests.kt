package pietrocaselani.github.io.upcomingtracker

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Lifecycle.Event.ON_RESUME
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.Observer
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import pietrocaselani.github.io.upcomingtracker.upcoming.UpcomingMoviesRepository
import pietrocaselani.github.io.upcomingtracker.upcoming.UpcomingMoviesViewModel
import pietrocaselani.github.io.upcomingtracker.upcoming.UpcomingMoviesViewState

@RunWith(MockitoJUnitRunner::class)
class UpcomingMoviesViewModelTests {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<UpcomingMoviesViewState>

    @Mock
    lateinit var repository: UpcomingMoviesRepository

    @Mock
    lateinit var lifeCycleOwner: LifecycleOwner

    lateinit var schedulers: Schedulers

    lateinit var lifecycle: LifecycleRegistry

    lateinit var viewModel: UpcomingMoviesViewModel

    @Before
    fun setup() {
        schedulers = TestSchedulers()
        lifecycle = LifecycleRegistry(lifeCycleOwner)
        viewModel = UpcomingMoviesViewModel(repository, schedulers)
    }

    @Test
    fun when_viewmodel_starts_state_should_be_loading_and_then_unavailable() {
        //Given
        lifecycle.handleLifecycleEvent(ON_RESUME)
        Mockito.`when`(repository.fetchMovies(1)).thenReturn(Single.just(emptyList()))

        //When
        viewModel.viewState.observeForever(observer)

        viewModel.start()

        //Then
        verify(observer).onChanged(UpcomingMoviesViewState.Loading)
        verify(observer).onChanged(UpcomingMoviesViewState.Unavailable)
    }

    @Test
    fun when_there_is_no_movies_available_state_should_be_unavailable() {
        //Given
        lifecycle.handleLifecycleEvent(ON_RESUME)
        Mockito.`when`(repository.fetchMovies(1)).thenReturn(Single.just(emptyList()))

        //When
        viewModel.viewState.observeForever(observer)
        viewModel.start()

        //Then
        verify(observer).onChanged(UpcomingMoviesViewState.Unavailable)
    }

    @Test fun when_repository_emits_error_state_should_be_error() {
        //Given
        lifecycle.handleLifecycleEvent(ON_RESUME)
        val errorMessage = "There is no internet connection"
        Mockito.`when`(repository.fetchMovies(1)).thenReturn(Single.error(Exception(errorMessage)))

        //When
        viewModel.viewState.observeForever(observer)
        viewModel.start()

        //Then
        verify(observer).onChanged(UpcomingMoviesViewState.Error(errorMessage))
    }

    @Test fun when_repository_emits_movies_state_should_be_movies_available() {
        //Given
        lifecycle.handleLifecycleEvent(ON_RESUME)
        val movies = listOf(movieMock(), movieMock(id = 10), movieMock(id = 30))
        Mockito.`when`(repository.fetchMovies(1)).thenReturn(Single.just(movies))

        //When
        viewModel.viewState.observeForever(observer)
        viewModel.start()

        //Then
        verify(observer).onChanged(UpcomingMoviesViewState.Available(movies))
    }
}