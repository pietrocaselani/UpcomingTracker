package pietrocaselani.github.io.upcomingtracker.details

import com.nytimes.android.external.store3.base.impl.BarCode
import com.nytimes.android.external.store3.base.impl.Store
import io.reactivex.Single
import pietrocaselani.github.io.upcomingtracker.Schedulers
import pietrocaselani.github.io.upcomingtracker.TMDBMovie
import pietrocaselani.github.io.upcomingtracker.entities.BackdropImageSize
import pietrocaselani.github.io.upcomingtracker.entities.MovieDetails
import pietrocaselani.github.io.upcomingtracker.entities.PosterImageSize
import pietrocaselani.github.io.upcomingtracker.extensions.backdropLink
import pietrocaselani.github.io.upcomingtracker.extensions.configurationBarCode
import pietrocaselani.github.io.upcomingtracker.extensions.parseReleaseDate
import pietrocaselani.github.io.upcomingtracker.extensions.posterLink
import pietrocaselani.github.io.upcomingtracker.tmdb.entities.Configuration
import pietrocaselani.github.io.upcomingtracker.tmdb.entities.Genre

class DetailsStoreRepository(private val movieDetailsStore: Store<TMDBMovie, BarCode>,
                             private val configurationStore: Store<Configuration, BarCode>,
                             private val schedulers: Schedulers) : DetailsRepository {
    override fun fetchDetails(movieId: Int): Single<MovieDetails> {
        val barCode = BarCode("movieDetails", movieId.toString())
        return movieDetailsStore.get(barCode)
                .flatMap(this::flatMapToEntity)
                .subscribeOn(schedulers.ioScheduler)
    }

    private fun flatMapToEntity(movie: TMDBMovie): Single<MovieDetails> {
        return configurationStore.get(configurationBarCode())
                .map { mapToEntity(movie, it) }
    }

    private fun mapToEntity(tmdbMovie: TMDBMovie, configuration: Configuration): MovieDetails {
        val posterLink = configuration.posterLink(PosterImageSize.W780, tmdbMovie.poster_path)
        val backdropLink = configuration.backdropLink(BackdropImageSize.W780, tmdbMovie.backdrop_path)
        val releaseDate = tmdbMovie.parseReleaseDate()

        return MovieDetails(tmdbMovie.id,
                tmdbMovie.title,
                tmdbMovie.overview,
                posterLink,
                backdropLink,
                releaseDate,
                tmdbMovie.vote_count,
                tmdbMovie.vote_average,
                tmdbMovie.genres,
                tmdbMovie.imdb_id,
                tmdbMovie.tagline)
    }

}