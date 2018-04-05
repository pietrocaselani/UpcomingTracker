package pietrocaselani.github.io.upcomingtracker

import pietrocaselani.github.io.upcomingtracker.entities.BackdropImageSize
import pietrocaselani.github.io.upcomingtracker.entities.Movie
import pietrocaselani.github.io.upcomingtracker.entities.PosterImageSize
import pietrocaselani.github.io.upcomingtracker.tmdb.entities.Configuration
import pietrocaselani.github.io.upcomingtracker.tmdb.entities.Images
import java.util.*

fun movieMock(id: Int = 268896,
              title: String = "Pacific Rim: Uprising",
              overview: String = "It has been ten years since The Battle of the Breach and the oceans are still, but restless. Vindicated by the victory at the Breach, the Jaeger program has evolved into the most powerful global defense force in human history. The PPDC now calls upon the best and brightest to rise up and become the next generation of heroes when the Kaiju threat returns.",
              posterLink: String? = "https://image.tmdb.org/t/p/w185/v5HlmJK9bdeHxN2QhaFP1ivjX3U.jpg",
              backdropLink: String? = "https://image.tmdb.org/t/p/w185/mo5EJsExrQCroqPDwUwp6jeq0xS.jpg",
              releaseDate: Date? = Date(1521601200000),
              voteCount: Int = 313,
              voteAverage: Float = 6.1f): Movie {
    return Movie(id, title, overview, posterLink, backdropLink, releaseDate, voteCount, voteAverage)
}

fun tmdbMovieMock(id: Int = 268896,
                  title: String = "Pacific Rim: Uprising",
                  overview: String = "It has been ten years since The Battle of the Breach and the oceans are still, but restless. Vindicated by the victory at the Breach, the Jaeger program has evolved into the most powerful global defense force in human history. The PPDC now calls upon the best and brightest to rise up and become the next generation of heroes when the Kaiju threat returns.",
                  posterPath: String? = "/v5HlmJK9bdeHxN2QhaFP1ivjX3U.jpg",
                  backdropPath: String? = "/mo5EJsExrQCroqPDwUwp6jeq0xS.jpg",
                  releaseDate: String? = "2018-03-21",
                  voteCount: Int = 313,
                  voteAverage: Float = 6.1f): TMDBMovie {
    return TMDBMovie(id, title, overview, posterPath, backdropPath, releaseDate, voteCount, voteAverage)
}

fun tmdbConfigurationMock(images: Images = tmdbImagesMock()): Configuration {
    return Configuration(images)
}

fun tmdbImagesMock(secureBaseURL: String = "https://image.tmdb.org/t/p/",
                   backdropSizes: List<String> = BackdropImageSize.names(),
                   posterSizes: List<String> = PosterImageSize.names()): Images {
    return Images(secureBaseURL, backdropSizes, posterSizes)
}