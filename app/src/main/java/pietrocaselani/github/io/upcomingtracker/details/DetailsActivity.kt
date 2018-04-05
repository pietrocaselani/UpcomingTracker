package pietrocaselani.github.io.upcomingtracker.details

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import pietrocaselani.github.io.upcomingtracker.R
import pietrocaselani.github.io.upcomingtracker.ViewModelFactory
import pietrocaselani.github.io.upcomingtracker.details.DetailsViewState.*
import pietrocaselani.github.io.upcomingtracker.entities.Movie
import pietrocaselani.github.io.upcomingtracker.entities.MovieDetails

class DetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val movie = intent.extras.getParcelable<Movie>(Movie.PARCELABLE_KEY)
        val params = mapOf(Movie.PARCELABLE_KEY to movie)

        viewModel = ViewModelFactory.create(DetailsViewModel::class.java, params)

        viewModel.viewState.observe(this, Observer {
            val viewState = it ?: Loading
            updateView(viewState)
        })

        detailsTransulcentBackground.alpha = 0.56f
    }

    override fun onStart() {
        super.onStart()

        viewModel.start()
    }

    private fun updateView(viewState: DetailsViewState) {
        when (viewState) {
            is Loading -> showLoading()
            is Error -> showError(viewState.message)
            is Available -> showDetails(viewState.movieDetails)
        }
    }

    private fun showDetails(movieDetails: MovieDetails) {
        detailsInfoTextView.visibility = GONE
        detailsProgressBar.visibility = GONE
        detailsScrollView.visibility = VISIBLE

        title = movieDetails.title

        val picasso = Picasso.get()

        picasso.load(movieDetails.posterLink).into(detailsBackgroundImageView)

        detailsTaglineTextView.text = movieDetails.tagline
        detailsOverviewTextView.text = movieDetails.overview
    }

    private fun showError(message: String) {
        detailsInfoTextView.text = message
        detailsInfoTextView.visibility = VISIBLE
        detailsScrollView.visibility = GONE
        detailsProgressBar.visibility = GONE
    }

    private fun showLoading() {
        detailsProgressBar.visibility = VISIBLE
        detailsInfoTextView.visibility = GONE
        detailsScrollView.visibility = GONE
    }
}
