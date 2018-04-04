package pietrocaselani.github.io.upcomingtracker.upcoming

import com.nytimes.android.external.store3.base.impl.BarCode
import com.nytimes.android.external.store3.base.impl.Store
import io.reactivex.Single
import okhttp3.HttpUrl
import pietrocaselani.github.io.upcomingtracker.Schedulers
import pietrocaselani.github.io.upcomingtracker.TMDBMovie
import pietrocaselani.github.io.upcomingtracker.entities.Movie
import pietrocaselani.github.io.upcomingtracker.entities.PosterImageSize
import pietrocaselani.github.io.upcomingtracker.tmdb.entities.Configuration
import pietrocaselani.github.io.upcomingtracker.tmdb.entities.PagedResponse

class UpcomingStoreRepository(private val moviesStore: Store<PagedResponse, BarCode>,
                              private val configurationStore: Store<Configuration, BarCode>,
                              private val schedulers: Schedulers) : UpcomingMoviesRepository {
    private val configurationKey = "configuration"
    private val configurationBarCode = BarCode(configurationKey, configurationKey)


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
                urlBuilder.newBuilder().addPathSegment(it).build().toString()
            }

            val backdropLink = it.backdrop_path?.let {
                urlBuilder.newBuilder().addPathSegment(it).build().toString()
            }

            Movie(it.id,
                    it.title,
                    it.overview,
                    posterLink,
                    backdropLink,
                    it.release_date,
                    it.vote_count,
                    it.vote_average)
        }
    }
}