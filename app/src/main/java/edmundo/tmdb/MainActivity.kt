package edmundo.tmdb

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import dagger.android.AndroidInjection
import edmundo.tmdb.adapter.UpcomingMoviesAdapter
import edmundo.tmdb.databinding.ActivityMainBinding
import edmundo.tmdb.model.Result
import edmundo.tmdb.utils.StateEnum
import edmundo.tmdb.viewModel.UpcomingMoviesViewModel
import edmundo.tmdb.viewModel.factory.UpcomingMoviesViewModelFactory
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var upcomingMoviesAdapter: UpcomingMoviesAdapter
    @Inject
    lateinit var upcomingMoviesViewModelFactory: UpcomingMoviesViewModelFactory

    lateinit var upcomingMoviesViewModel: UpcomingMoviesViewModel

    lateinit var mLayoutManager: LinearLayoutManager

    lateinit var activityMainBinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        upcomingMoviesViewModel = ViewModelProviders.of(this, upcomingMoviesViewModelFactory).get(UpcomingMoviesViewModel::class.java)

        activityMainBinding.setVariable(BR.upComingMovie, upcomingMoviesViewModel)
        activityMainBinding.executePendingBindings()

        initAdapter()
        initState()
    }

    private fun initAdapter() {
        mLayoutManager = LinearLayoutManager(this)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        activityMainBinding.rvUpcomingMovies.layoutManager = mLayoutManager
        activityMainBinding.rvUpcomingMovies.adapter = upcomingMoviesAdapter
        upcomingMoviesViewModel.movieList.observe(this, Observer {
            upcomingMoviesAdapter.submitList(it)
        })
    }

    private fun setupList(movies: PagedList<Result>) {
//        upcomingMoviesViewModel.addMoviesToList(movies)
        mLayoutManager = LinearLayoutManager(this)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        activityMainBinding.rvUpcomingMovies.layoutManager = mLayoutManager
        activityMainBinding.rvUpcomingMovies.adapter = upcomingMoviesAdapter

        upcomingMoviesAdapter.submitList(movies)
    }

    private fun subscribeToLiveData(){

//        upcomingMoviesViewModel.getUpComingMovies()

        upcomingMoviesViewModel.vmLoader().observe(this, Observer {
//            activityMainBinding.pbUpcomingMovies.visibility = if (!it!!) View.GONE else View.VISIBLE
//            activityMainBinding.rvUpcomingMovies.visibility = if(!it) View.VISIBLE else View.GONE

        })

        upcomingMoviesViewModel.vmError().observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
//
//        upcomingMoviesViewModel.movieList.observe(this, Observer {
//            setupList(it!!)
//        })
    }

    private fun initState() {
        upcomingMoviesViewModel.getState().observe(this, Observer { state ->
            activityMainBinding.pbUpcomingMovies.visibility = if (upcomingMoviesViewModel.listIsEmpty() && state == StateEnum.LOADING) View.VISIBLE else View.GONE
            activityMainBinding.rvUpcomingMovies.visibility = if (state == StateEnum.DONE) View.VISIBLE else View.GONE
            if (!upcomingMoviesViewModel.listIsEmpty()) {
                upcomingMoviesAdapter.setState(state ?: StateEnum.DONE)
            }
        })
    }
}
