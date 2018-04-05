package pietrocaselani.github.io.upcomingtracker.extensions

import com.nytimes.android.external.store3.base.impl.BarCode
import okhttp3.HttpUrl
import pietrocaselani.github.io.upcomingtracker.entities.BackdropImageSize
import pietrocaselani.github.io.upcomingtracker.entities.PosterImageSize
import pietrocaselani.github.io.upcomingtracker.tmdb.entities.Configuration

fun configurationBarCode(): BarCode {
    val configurationKey = "configuration"
    return BarCode(configurationKey, configurationKey)
}

fun Configuration.posterLink(size: PosterImageSize, path: String?): String? {
    val baseURLBuilder = secureImageURL(size.name.toLowerCase()).newBuilder()

    return path?.let {
        val trimmedPath = path.trimStart('/')
        baseURLBuilder.addPathSegment(trimmedPath).build().toString()
    }
}

fun Configuration.backdropLink(size: BackdropImageSize, path: String?): String? {
    val baseURLBuilder = secureImageURL(size.name.toLowerCase()).newBuilder()

    return path?.let {
        val trimmedPath = path.trimStart('/')
        baseURLBuilder.addPathSegment(trimmedPath).build().toString()
    }
}

fun Configuration.secureImageURL(sizeName: String): HttpUrl {
    return HttpUrl.parse(this.images.secure_base_url)!!
            .newBuilder()
            .addPathSegment(sizeName)
            .build()
}