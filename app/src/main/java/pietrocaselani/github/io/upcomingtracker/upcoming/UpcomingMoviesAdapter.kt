package pietrocaselani.github.io.upcomingtracker.upcoming

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import pietrocaselani.github.io.upcomingtracker.entities.Movie
import pietrocaselani.github.io.upcomingtracker.upcoming.UpcomingMoviesAdapter.MovieViewHolder

class UpcomingMoviesAdapter(private val movies: MutableList<Movie> = mutableListOf()): RecyclerView.Adapter<MovieViewHolder>() {

    fun addMovies(newMovies: List<Movie>) {
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.count()
    }

    override fun onBindViewHolder(holder: MovieViewHolder?, position: Int) {
        val movie = movies[position]
        holder?.textview?.text = movie.title
    }

    class MovieViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val textview = itemView?.findViewById<TextView>(android.R.id.text1)
    }
}