package pietrocaselani.github.io.upcomingtracker

import com.nytimes.android.external.store3.base.impl.BarCode
import com.nytimes.android.external.store3.base.impl.StoreBuilder
import com.nytimes.android.external.store3.util.KeyParser
import io.reactivex.Single
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import pietrocaselani.github.io.upcomingtracker.tmdb.entities.Configuration
import pietrocaselani.github.io.upcomingtracker.tmdb.entities.PagedResponse
import pietrocaselani.github.io.upcomingtracker.upcoming.UpcomingStoreRepository

@RunWith(MockitoJUnitRunner::class)
class UpcomingStoreRepositoryTests {

    @Test
    fun should_fetch_movies_from_network_when_cache_is_empty() {
        //Given
        var moviesCalls = 0
        var configurationCalls = 0

        val moviesStore = StoreBuilder.parsedWithKey<BarCode, List<TMDBMovie>, PagedResponse>()
                .fetcher { key: BarCode ->
                    moviesCalls++
                    val movies = listOf(tmdbMovieMock(), tmdbMovieMock(id = 10))
                    Single.just(movies)
                }.parser({ key, raw ->
                    PagedResponse(key.key.toIntOrNull() ?: 1, raw, 10, 10)
                })
                .open()

        val configurationStore = StoreBuilder.parsedWithKey<BarCode, Configuration, Configuration>()
                .fetcher { key: BarCode ->
                    configurationCalls++
                    Single.just(tmdbConfigurationMock())
                }.open()

        val schedulers = TestSchedulers()

        val repository = UpcomingStoreRepository(moviesStore, configurationStore, schedulers)

        //When
        val observer = repository.fetchMovies(1).test()

        //Then
        val expectedMovies = listOf(movieMock(), movieMock(id = 10))
        observer.assertComplete()
        observer.assertNoErrors()
        observer.assertResult(expectedMovies)
        assertEquals(moviesCalls, 1)
        assertEquals(configurationCalls, 1)
    }
}