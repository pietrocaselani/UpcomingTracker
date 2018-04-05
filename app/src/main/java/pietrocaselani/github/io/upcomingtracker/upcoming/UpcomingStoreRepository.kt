package pietrocaselani.github.io.upcomingtracker.upcoming

import com.nytimes.android.external.store3.base.impl.BarCode
import com.nytimes.android.external.store3.base.impl.Store
import io.reactivex.Single
import okhttp3.HttpUrl
import pietrocaselani.github.io.upcomingtracker.Schedulers
import pietrocaselani.github.io.upcomingtracker.entities.Movie
import pietrocaselani.github.io.upcomingtracker.entities.PosterImageSize
import pietrocaselani.github.io.upcomingtracker.tmdb.entities.Configuration
import pietrocaselani.github.io.upcomingtracker.tmdb.entities.PagedResponse
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class UpcomingStoreRepository(private val moviesStore: Store<PagedResponse, BarCode>,
                              private val configurationStore: Store<Configuration, BarCode>,
                              private val schedulers: Schedulers) : UpcomingMoviesRepository {
    private val configurationKey = "configuration"
    private val configurationBarCode = BarCode(configurationKey, configurationKey)
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun fetchMovies(page: Int): Single<List<Movie>> {
        val barCode = BarCode("movies", page.toString())
        return moviesStore.get(barCode)
                .flatMap(this::flatMapToEntity)
                .subscribeOn(schedulers.ioScheduler)
    }

    private fun flatMapToEntity(pagedResponse: PagedResponse): Single<List<Movie>> {
        return configurationStore.get(configurationBarCode)
                .map { mapToEntity(pagedResponse, it) }
    }

    private fun mapToEntity(pagedResponse: PagedResponse, configuration: Configuration): List<Movie> {
        val urlBuilder = HttpUrl.parse(configuration.images.secure_base_url)!!
                .newBuilder()
                .addPathSegment(PosterImageSize.W185.name.toLowerCase())
                .build()

        return pagedResponse.results.map {
            val posterLink = it.poster_path?.let {
                val l = it.removeRange(0..0)
                urlBuilder.newBuilder().addPathSegment(l).build().toString()
            }

            val backdropLink = it.backdrop_path?.let {
                val l = it.removeRange(0..0)
                urlBuilder.newBuilder().addPathSegment(l).build().toString()
            }

            val releaseDate = try {
                dateFormatter.parse(it.release_date)
            } catch (exception: ParseException) {
                null
            }

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