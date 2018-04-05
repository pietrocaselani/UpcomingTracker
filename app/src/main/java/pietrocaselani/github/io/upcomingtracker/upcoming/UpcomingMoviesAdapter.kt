package pietrocaselani.github.io.upcomingtracker.upcoming

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.squareup.picasso.PicassoProvider
import pietrocaselani.github.io.upcomingtracker.R
import pietrocaselani.github.io.upcomingtracker.entities.Movie
import pietrocaselani.github.io.upcomingtracker.upcoming.UpcomingMoviesAdapter.MovieViewHolder
import java.text.SimpleDateFormat
import java.util.*

class UpcomingMoviesAdapter(private val movies: MutableList<Movie> = mutableListOf()): RecyclerView.Adapter<MovieViewHolder>() {
    private val dateFormatter = SimpleDateFormat("dd MMM yy", Locale.getDefault())
    private val picasso = Picasso.get()

    fun addMovies(newMovies: List<Movie>) {
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.movie_cell, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.count()
    }

    override fun onBindViewHolder(holder: MovieViewHolder?, position: Int) {
        val movie = movies[position]
        val link = movie.posterLink ?: movie.backdropLink

        picasso.load(link).into(holder?.imageView)

        var detailText: String = dateFormatter.format(movie.releaseDate)

        if (detailText.isNullOrEmpty()) {
            detailText = "TBA"
        }

        holder?.titleTextView?.text = movie.title
        holder?.detailTextView?.text = detailText
    }

    class MovieViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val titleTextView = itemView?.findViewById<TextView>(R.id.movieTitleTextView)
        val detailTextView = itemView?.findViewById<TextView>(R.id.movieDetailTextView)
        val imageView = itemView?.findViewById<ImageView>(R.id.moviePosterImageView)
    }
}