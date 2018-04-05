package pietrocaselani.github.io.upcomingtracker.upcoming

import com.nytimes.android.external.store3.base.impl.BarCode
import com.nytimes.android.external.store3.base.impl.Store
import io.reactivex.Single
import okhttp3.HttpUrl
import pietrocaselani.github.io.upcomingtracker.Schedulers
import pietrocaselani.github.io.upcomingtracker.entities.BackdropImageSize
import pietrocaselani.github.io.upcomingtracker.entities.Movie
import pietrocaselani.github.io.upcomingtracker.entities.PosterImageSize
import pietrocaselani.github.io.upcomingtracker.extensions.backdropLink
import pietrocaselani.github.io.upcomingtracker.extensions.configurationBarCode
import pietrocaselani.github.io.upcomingtracker.extensions.parseReleaseDate
import pietrocaselani.github.io.upcomingtracker.extensions.posterLink
import pietrocaselani.github.io.upcomingtracker.tmdb.entities.Configuration
import pietrocaselani.github.io.upcomingtracker.tmdb.entities.PagedResponse
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class UpcomingStoreRepository(private val moviesStore: Store<PagedResponse, BarCode>,
                              private val configurationStore: Store<Configuration, BarCode>,
                              private val schedulers: Schedulers) : UpcomingMoviesRepository {
    override fun fetchMovies(page: Int): Single<List<Movie>> {
        val barCode = BarCode("movies", page.toString())
        return moviesStore.get(barCode)
                .flatMap(this::flatMapToEntity)
                .subscribeOn(schedulers.ioScheduler)
    }

    private fun flatMapToEntity(pagedResponse: PagedResponse): Single<List<Movie>> {
        return configurationStore.get(configurationBarCode())
                .map { mapToEntity(pagedResponse, it) }
    }

    private fun mapToEntity(pagedResponse: PagedResponse, configuration: Configuration): List<Movie> {
        return pagedResponse.results.map {
            val posterLink = configuration.posterLink(PosterImageSize.W185, it.poster_path)
            val backdropLink = configuration.backdropLink(BackdropImageSize.W780, it.backdrop_path)
            val releaseDate = it.parseReleaseDate()

            Movie(it.id,
                    it.title,
                    it.overview,
                    posterLink,
                    backdropLink,
                    releaseDate,
                    it.vote_count,
                    it.vote_average)
        }
    }
}