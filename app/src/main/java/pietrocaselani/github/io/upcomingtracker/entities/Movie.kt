package pietrocaselani.github.io.upcomingtracker.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
data class Movie(
        val id: Int,
        val title: String,
        val overview: String,
        val posterLink: String?,
        val backdropLink: String?,
        val releaseDate: Date?,
        val voteCount: Int,
        val voteAverage: Float
): Parcelable {
    companion object {
        val PARCELABLE_KEY = "movie"
    }
}