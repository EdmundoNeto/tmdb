package edmundo.tmdb.adapter

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import edmundo.tmdb.model.Result
import edmundo.tmdb.network.MovieRepository
import io.reactivex.disposables.CompositeDisposable

class UpcomingMoviesDataSourceFactory(private val compositeDisposable: CompositeDisposable,
                                      private val movieRepository: MovieRepository): DataSource.Factory<Int, Result>() {

    val upcomingMoviesDataSource = MutableLiveData<UpcomingMoviesDataSource>()

    override fun create(): DataSource<Int, Result> {
        val upcomingDataSource = UpcomingMoviesDataSource(movieRepository, compositeDisposable)
        upcomingMoviesDataSource.postValue(upcomingDataSource)
        return upcomingDataSource
    }
}