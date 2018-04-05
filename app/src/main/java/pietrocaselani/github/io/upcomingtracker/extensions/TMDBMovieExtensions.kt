package pietrocaselani.github.io.upcomingtracker.extensions

import pietrocaselani.github.io.upcomingtracker.TMDBMovie
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun TMDBMovie.parseReleaseDate(): Date? {
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return try {
        dateFormatter.parse(this.release_date)
    } catch (exception: ParseException) {
        null
    }
}