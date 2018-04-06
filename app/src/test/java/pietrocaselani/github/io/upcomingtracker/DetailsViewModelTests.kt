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
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import pietrocaselani.github.io.upcomingtracker.details.DetailsRepository
import pietrocaselani.github.io.upcomingtracker.details.DetailsViewModel
import pietrocaselani.github.io.upcomingtracker.details.DetailsViewState

@RunWith(MockitoJUnitRunner::class)
class DetailsViewModelTests {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<DetailsViewState>

    @Mock
    lateinit var repository: DetailsRepository

    @Mock
    lateinit var lifeCycleOwner: LifecycleOwner

    lateinit var schedulers: Schedulers

    lateinit var lifecycle: LifecycleRegistry

    lateinit var viewModel: DetailsViewModel

    @Before
    fun setup() {
        schedulers = TestSchedulers()
        lifecycle = LifecycleRegistry(lifeCycleOwner)
        viewModel = DetailsViewModel(movieMock(), repository, schedulers)
    }

    @Test
    fun when_viewmodel_starts_state_should_be_loading() {
        //Given
        lifecycle.handleLifecycleEvent(ON_RESUME)
        `when`(repository.fetchDetails(ArgumentMatchers.anyInt())).thenReturn(Single.just(movieDetailsMock()))

        //When
        viewModel.viewState.observeForever(observer)
        viewModel.start()

        //Then
        verify(observer).onChanged(DetailsViewState.Loading)
    }

    @Test
    fun when_repository_emits_error_state_should_be_error() {
        //Given
        lifecycle.handleLifecycleEvent(ON_RESUME)
        val errorMessage = "There is no internet connection"
        Mockito.`when`(repository.fetchDetails(ArgumentMatchers.anyInt())).thenReturn(Single.error(Exception(errorMessage)))

        //When
        viewModel.viewState.observeForever(observer)
        viewModel.start()

        //Then
        verify(observer).onChanged(DetailsViewState.Error(errorMessage))
    }

    @Test
    fun when_repository_emits_details_state_should_be_available() {
        //Given
        lifecycle.handleLifecycleEvent(ON_RESUME)
        val details = movieDetailsMock()
        Mockito.`when`(repository.fetchDetails(ArgumentMatchers.anyInt())).thenReturn(Single.just(details))

        //When
        viewModel.viewState.observeForever(observer)
        viewModel.start()

        //Then
        verify(observer).onChanged(DetailsViewState.Available(movieDetails = details))
    }
}