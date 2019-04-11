package edmundo.tmdb

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import dagger.android.AndroidInjection
import edmundo.tmdb.databinding.MovieDetailActivityBinding
import edmundo.tmdb.viewModel.MovieDetailViewModel
import edmundo.tmdb.viewModel.factory.MovieDetailViewModelFactory
import javax.inject.Inject

class MovieDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var movieDetailViewModelFactory: MovieDetailViewModelFactory

    lateinit var movieDetailViewModel: MovieDetailViewModel

    lateinit var activityMovieDetailBinding: MovieDetailActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        activityMovieDetailBinding = DataBindingUtil.setContentView(this, R.layout.movie_detail_activity)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        movieDetailViewModel = ViewModelProviders.of(this, movieDetailViewModelFactory).get(MovieDetailViewModel::class.java)

        subscribeToLiveData()
    }

    private fun subscribeToLiveData(){

        val movieId = intent.extras.getSerializable("movieId") as Int
        movieDetailViewModel.getMovieDetail(movieId)

        movieDetailViewModel.vmLoader().observe(this, Observer {
            activityMovieDetailBinding.clHeader.visibility = if(!it!!) View.VISIBLE else View.GONE

        })

        movieDetailViewModel.vmError().observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        movieDetailViewModel.moviedetailResult().observe(this, Observer {
            activityMovieDetailBinding.setVariable(BR.movieDetail, it!!)
            activityMovieDetailBinding.executePendingBindings()
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}