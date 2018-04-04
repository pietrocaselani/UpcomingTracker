package pietrocaselani.github.io.upcomingtracker.upcoming

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_upcoming_movies.*
import pietrocaselani.github.io.upcomingtracker.R
import pietrocaselani.github.io.upcomingtracker.ViewModelFactory
import pietrocaselani.github.io.upcomingtracker.entities.Movie
import pietrocaselani.github.io.upcomingtracker.upcoming.UpcomingMoviesViewState.*

class UpcomingMoviesActivity : AppCompatActivity() {

    private lateinit var viewModel: UpcomingMoviesViewModel
    private val adapter = UpcomingMoviesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_movies)

        upcomingMoviesRecyclerView.adapter = adapter
        upcomingMoviesRecyclerView.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelFactory.create(UpcomingMoviesViewModel::class.java)

        viewModel.viewState.observe(this, Observer {
            val viewState = it ?: Loading
            updateView(viewState)
        })
    }

    override fun onStart() {
        super.onStart()

        viewModel.start()
    }

    private fun updateView(viewState: UpcomingMoviesViewState) {
        when (viewState) {
            is Loading -> showLoading()
            is Available -> showMovies(viewState.movies)
            is Unavailable -> showEmptyView()
            is Error -> showError(viewState.message)
        }
    }

    private fun showMovies(movies: List<Movie>) {
        upcomingMoviesProgressBar.visibility = View.GONE
        upcomingMoviesTextView.visibility = View.GONE
        upcomingMoviesRecyclerView.visibility = View.VISIBLE

        adapter.addMovies(movies)
    }

    private fun showEmptyView() {
        upcomingMoviesTextView.visibility = View.VISIBLE
        upcomingMoviesTextView.text = "Nenhum filme no momento"
        upcomingMoviesProgressBar.visibility = View.GONE
        upcomingMoviesRecyclerView.visibility = View.GONE
    }

    private fun showError(message: String) {
        upcomingMoviesTextView.visibility = View.VISIBLE
        upcomingMoviesTextView.text = message
        upcomingMoviesProgressBar.visibility = View.GONE
        upcomingMoviesRecyclerView.visibility = View.GONE
    }

    private fun showLoading() {
        upcomingMoviesProgressBar.visibility = View.VISIBLE
        upcomingMoviesTextView.visibility = View.GONE
        upcomingMoviesRecyclerView.visibility = View.GONE
    }
}
