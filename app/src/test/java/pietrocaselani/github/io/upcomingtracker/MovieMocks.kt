package pietrocaselani.github.io.upcomingtracker

import pietrocaselani.github.io.upcomingtracker.entities.Movie

fun movieMock(id: Int = 268896,
              title: String = "Pacific Rim: Uprising",
              overview: String = "It has been ten years since The Battle of the Breach and the oceans are still, but restless. Vindicated by the victory at the Breach, the Jaeger program has evolved into the most powerful global defense force in human history. The PPDC now calls upon the best and brightest to rise up and become the next generation of heroes when the Kaiju threat returns.",
              posterLink: String? = "https://image.tmdb.org/t/p/w185/v5HlmJK9bdeHxN2QhaFP1ivjX3U.jpg",
              backdropLink: String?  = "https://image.tmdb.org/t/p/w185/mo5EJsExrQCroqPDwUwp6jeq0xS.jpg",
              releaseDate: String? = "2018-03-21",
              voteCount: Int = 313,
              voteAverage: Float = 6.1f): Movie {
    return Movie(id, title, overview, posterLink, backdropLink, releaseDate, voteCount, voteAverage)
}