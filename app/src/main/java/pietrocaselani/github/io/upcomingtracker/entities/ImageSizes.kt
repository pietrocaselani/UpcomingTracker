package pietrocaselani.github.io.upcomingtracker.entities

enum class PosterImageSize {
    W92,
    W154,
    W185,
    W342,
    W500,
    W780,
    ORIGINAL;

    companion object {
        fun names(): List<String> {
            return PosterImageSize.values().map { it.name.toLowerCase() }
        }
    }
}

enum class BackdropImageSize {
    W300,
    W780,
    W1280,
    ORIGINAL;

    companion object {
        fun names(): List<String> {
            return PosterImageSize.values().map { it.name.toLowerCase() }
        }
    }
}