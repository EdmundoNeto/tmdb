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

    var movieList: LiveData<PagedList<Result>>
    private val compositeDisposable = CompositeDisposable()
    private val upcomingMoviesDataSourceFactory = UpcomingMoviesDataSourceFactory(compositeDisposable, movieRepository)

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setInitialLoadSizeHint(20 * 2)
            .setEnablePlaceholders(false)
            .build()
        movieList = LivePagedListBuilder<Int, Result>(upcomingMoviesDataSourceFactory, config).build()
    }

    fun getState(): LiveData<StateEnum> = Transformations.switchMap<UpcomingMoviesDataSource,
            StateEnum>(upcomingMoviesDataSourceFactory.upcomingMoviesDataSource, UpcomingMoviesDataSource::state)

    fun listIsEmpty(): Boolean {
        return movieList.value?.isEmpty() ?: true
    }

    var upComingMovies: MutableLiveData<List<Result>> = MutableLiveData()

    val movieArrayList: ObservableList<Result> = ObservableArrayList()

    lateinit var disposableObserver: DisposableObserver<List<Result>>

    fun addMoviesToList(list: List<Result>) {
        movieArrayList.clear()
        movieArrayList.addAll(list)
    }


    fun upComingMoviesResult(): LiveData<List<Result>> {
        return upComingMovies
    }

//    fun getUpComingMovies() {
//        disposableObserver = object: DisposableObserver<List<Result>>() {
//            override fun onComplete() {
//                vmLoader.postValue(false)
//            }
//
//            override fun onNext(t: List<Result>) {
//                upComingMovies.postValue(t)
//            }
//
//            override fun onError(e: Throwable) {
//                vmLoader.postValue(false)
//                vmError.postValue(e.message)
//            }
//        }
//
//        movieRepository.getUpcoming()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(disposableObserver)
//    }
}