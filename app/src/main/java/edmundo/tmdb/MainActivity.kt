package edmundo.tmdb

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import dagger.android.AndroidInjection
import edmundo.tmdb.adapter.UpcomingMoviesAdapter
import edmundo.tmdb.utils.StateEnum
import edmundo.tmdb.viewModel.UpcomingMoviesViewModel
import edmundo.tmdb.viewModel.factory.UpcomingMoviesViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var upcomingMoviesAdapter: UpcomingMoviesAdapter
    @Inject
    lateinit var upcomingMoviesViewModelFactory: UpcomingMoviesViewModelFactory

    lateinit var upcomingMoviesViewModel: UpcomingMoviesViewModel

    lateinit var mLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        upcomingMoviesViewModel = ViewModelProviders.of(this, upcomingMoviesViewModelFactory).get(UpcomingMoviesViewModel::class.java)

        initAdapter()
        initState()
    }

    private fun initAdapter() {
        mLayoutManager = LinearLayoutManager(this)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rvUpcomingMovies.layoutManager = mLayoutManager
        rvUpcomingMovies.adapter = upcomingMoviesAdapter
        upcomingMoviesViewModel.movieList.observe(this, Observer {
            upcomingMoviesAdapter.submitList(it)
        })
    }

    private fun initState() {
        upcomingMoviesViewModel.getState().observe(this, Observer { state ->
            pbUpcomingMovies.visibility = if (upcomingMoviesViewModel.listIsEmpty() && state == StateEnum.LOADING) View.VISIBLE else View.GONE
            rvUpcomingMovies.visibility = if (state == StateEnum.DONE) View.VISIBLE else View.GONE
            if (!upcomingMoviesViewModel.listIsEmpty()) {
                upcomingMoviesAdapter.setState(state ?: StateEnum.DONE)
            }
        })
    }

}
