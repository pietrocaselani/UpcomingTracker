package pietrocaselani.github.io.upcomingtracker

import com.nytimes.android.external.fs3.SourcePersisterFactory
import com.nytimes.android.external.store3.base.impl.BarCode
import com.nytimes.android.external.store3.base.impl.Store
import com.nytimes.android.external.store3.base.impl.StoreBuilder
import com.nytimes.android.external.store3.middleware.moshi.MoshiParserFactory
import com.squareup.moshi.Moshi
import io.reactivex.Single
import okhttp3.OkHttpClient
import okio.BufferedSource
import pietrocaselani.github.io.upcomingtracker.entities.MovieDetails
import pietrocaselani.github.io.upcomingtracker.tmdb.TMDB
import pietrocaselani.github.io.upcomingtracker.tmdb.entities.Configuration
import pietrocaselani.github.io.upcomingtracker.tmdb.entities.Movie
import pietrocaselani.github.io.upcomingtracker.tmdb.entities.PagedResponse

typealias TMDBMovie = pietrocaselani.github.io.upcomingtracker.tmdb.entities.Movie

object Environment {
    val tmdb: TMDB
    val moviesStore: Store<PagedResponse, BarCode>
    val configurationStore: Store<Configuration, BarCode>
    val movieDetailsStore: Store<TMDBMovie, BarCode>
    val schedulers: Schedulers = DefaultSchedulers()
    private val persister = SourcePersisterFactory.create(UpcomingTrackerApplication.INSTANCE.cacheDir)

    init {
        val moshi = Moshi.Builder()
                .build()

        tmdb = TMDB.Builder()
                .apiKey(BuildConfig.TMDB_API_KEY)
                .client(OkHttpClient())
                .build()

        val moviePagedType = PagedResponse::class.java
        val configurationType = Configuration::class.java
        val tmdbMovieType = TMDBMovie::class.java

        val movieParser = MoshiParserFactory.createSourceParser<PagedResponse>(moshi, moviePagedType)

        moviesStore = StoreBuilder.parsedWithKey<BarCode, BufferedSource, PagedResponse>()
                .fetcher(this::moviesFetcher)
                .parser(movieParser)
                .persister(persister)
                .open()

        val configurationParser = MoshiParserFactory.createSourceParser<Configuration>(moshi, configurationType)

        configurationStore = StoreBuilder.parsedWithKey<BarCode, BufferedSource, Configuration>()
                .fetcher(this::configurationFetcher)
                .parser(configurationParser)
                .persister(persister)
                .open()

        val movieDetailsParser = MoshiParserFactory.createSourceParser<TMDBMovie>(moshi, tmdbMovieType)

        movieDetailsStore = StoreBuilder.parsedWithKey<BarCode, BufferedSource, TMDBMovie>()
                .fetcher(this::detailsFetcher)
                .parser(movieDetailsParser)
                .persister(persister)
                .open()
    }

    private fun moviesFetcher(barCode: BarCode): Single<BufferedSource> {
        return tmdb.movies().upcoming(barCode.key.toIntOrNull() ?: 1).map { it.source() }
    }

    private fun configurationFetcher(barCode: BarCode): Single<BufferedSource> {
        return tmdb.configuration().configuration().map { it.source() }
    }

    private fun detailsFetcher(barCode: BarCode): Single<BufferedSource> {
        return tmdb.movies().details(barCode.key.toInt()).map { it.source() }
    }
}