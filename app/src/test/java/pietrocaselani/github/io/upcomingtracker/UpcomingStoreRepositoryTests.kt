package pietrocaselani.github.io.upcomingtracker

import com.nytimes.android.external.store3.base.impl.BarCode
import com.nytimes.android.external.store3.base.impl.Store
import com.nytimes.android.external.store3.base.impl.StoreBuilder
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import pietrocaselani.github.io.upcomingtracker.tmdb.entities.Configuration
import pietrocaselani.github.io.upcomingtracker.tmdb.entities.PagedResponse
import pietrocaselani.github.io.upcomingtracker.upcoming.UpcomingStoreRepository

@RunWith(MockitoJUnitRunner::class)
class UpcomingStoreRepositoryTests {
    private var moviesNetworkCalls: Int = 0
    private var configurationNetworkCalls: Int = 0
    private lateinit var moviesPersister: PersisterMock<List<TMDBMovie>>
    private lateinit var configurationPersister: PersisterMock<Configuration>

    private fun setupMoviesStore(singleFetcher: Single<List<TMDBMovie>>): Store<PagedResponse, BarCode> {
        return StoreBuilder.parsedWithKey<BarCode, List<TMDBMovie>, PagedResponse>()
                .fetcher { key: BarCode ->
                    moviesNetworkCalls++
                    singleFetcher
                }.persister(moviesPersister)
                .parser({ key, raw ->
                    PagedResponse(key.key.toIntOrNull() ?: 1, raw, 10, 10)
                })
                .open()
    }

    private fun setupConfigurationStore(singleFetcher: Single<Configuration>): Store<Configuration, BarCode> {
        return StoreBuilder.parsedWithKey<BarCode, Configuration, Configuration>()
                .fetcher { key: BarCode ->
                    configurationNetworkCalls++
                    singleFetcher
                }.persister(configurationPersister)
                .open()
    }

    private fun setupMoviesPersister(movies: Map<BarCode, List<TMDBMovie>>, error: Throwable? = null) {
        moviesPersister = PersisterMock(movies, error)
    }

    private fun setupConfigurationPersister(configuration: Map<BarCode, Configuration>, error: Throwable? = null) {
        configurationPersister = PersisterMock(configuration, error)
    }

    @Before
    fun setup() {
        moviesNetworkCalls = 0
        configurationNetworkCalls = 0
    }

    @Test
    fun should_fetch_movies_from_network_and_save_on_cache_when_cache_is_empty() {
        //Given
        setupMoviesPersister(emptyMap())
        setupConfigurationPersister(emptyMap())

        val movies = listOf(tmdbMovieMock(), tmdbMovieMock(id = 10))
        val configurationMock = tmdbConfigurationMock()

        val moviesStore = setupMoviesStore(Single.just(movies))
        val configurationStore = setupConfigurationStore(Single.just(configurationMock))
        val schedulers = TestSchedulers()
        val repository = UpcomingStoreRepository(moviesStore, configurationStore, schedulers)

        //When
        val observer = repository.fetchMovies(1).test()

        //Then
        val expectedMovies = listOf(movieMock(), movieMock(id = 10))
        observer.assertComplete()
        observer.assertNoErrors()
        observer.assertResult(expectedMovies)
        assertEquals(moviesNetworkCalls, 1)
        assertEquals(configurationNetworkCalls, 1)
        assertEquals(moviesPersister.writeCalls, 1)
        assertEquals(configurationPersister.writeCalls, 1)
        assertEquals(moviesPersister.readCalls, 2)
        assertEquals(configurationPersister.readCalls, 2)
    }

    @Test
    fun should_fetch_movies_without_poster_and_backdrop_path() {
        //Given
        setupMoviesPersister(emptyMap())
        setupConfigurationPersister(emptyMap())
        val movies = listOf(tmdbMovieMock(posterPath = null), tmdbMovieMock(backdropPath = null))
        val moviesStore = setupMoviesStore(Single.just(movies))
        val configurationStore = setupConfigurationStore(Single.just(tmdbConfigurationMock()))
        val schedulers = TestSchedulers()
        val repository = UpcomingStoreRepository(moviesStore, configurationStore, schedulers)

        //When
        val observer = repository.fetchMovies(1).test()

        //Then
        val expectedMovies = listOf(movieMock(posterLink = null), movieMock(backdropLink = null))
        observer.assertComplete()
        observer.assertNoErrors()
        observer.assertResult(expectedMovies)
        assertEquals(moviesNetworkCalls, 1)
        assertEquals(configurationNetworkCalls, 1)
    }

    @Test
    fun should_fetch_from_cache_and_dont_hit_network_when_there_is_movies_on_cache() {
        //Given
        val movies = listOf(tmdbMovieMock(), tmdbMovieMock(id = 10))

        setupMoviesPersister(mapOf(BarCode("movies", "1") to movies))
        setupConfigurationPersister(emptyMap())

        val moviesStore = setupMoviesStore(Single.just(movies))
        val configurationStore = setupConfigurationStore(Single.just(tmdbConfigurationMock()))
        val schedulers = TestSchedulers()
        val repository = UpcomingStoreRepository(moviesStore, configurationStore, schedulers)

        //When
        val observer = repository.fetchMovies(1).test()

        //Then
        val expectedMovies = listOf(movieMock(), movieMock(id = 10))
        observer.assertComplete()
        observer.assertNoErrors()
        observer.assertResult(expectedMovies)
        assertEquals(moviesNetworkCalls, 0)
        assertEquals(configurationNetworkCalls, 1)
        assertEquals(moviesPersister.writeCalls, 0)
        assertEquals(configurationPersister.writeCalls, 1)
        assertEquals(moviesPersister.readCalls, 1)
        assertEquals(configurationPersister.readCalls, 2)
    }
}