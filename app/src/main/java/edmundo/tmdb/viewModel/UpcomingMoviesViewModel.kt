package edmundo.tmdb.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import edmundo.tmdb.model.Result
import edmundo.tmdb.network.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import edmundo.tmdb.adapter.UpcomingMoviesDataSource
import edmundo.tmdb.adapter.UpcomingMoviesDataSourceFactory
import edmundo.tmdb.utils.StateEnum
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class UpcomingMoviesViewModel @Inject constructor(private val movieRepository: MovieRepository): BaseViewModel() {

    private val pageSize = 10
    var movieList: LiveData<PagedList<Result>>
    private val compositeDisposable = CompositeDisposable()
    private val upcomingMoviesDataSourceFactory: UpcomingMoviesDataSourceFactory

    init {
        upcomingMoviesDataSourceFactory = UpcomingMoviesDataSourceFactory(compositeDisposable, movieRepository)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()
        movieList = LivePagedListBuilder<Int, Result>(upcomingMoviesDataSourceFactory, config).build()
    }

    fun getState(): LiveData<StateEnum> = Transformations.switchMap<UpcomingMoviesDataSource,
            StateEnum>(upcomingMoviesDataSourceFactory.upcomingMoviesDataSource, UpcomingMoviesDataSource::state)

    fun listIsEmpty(): Boolean {
        return movieList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}