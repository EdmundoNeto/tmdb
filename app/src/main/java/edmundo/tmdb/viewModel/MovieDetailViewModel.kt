package edmundo.tmdb.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import edmundo.tmdb.model.MovieDetail
import edmundo.tmdb.model.Result
import edmundo.tmdb.network.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(private val movieRepository: MovieRepository): BaseViewModel() {

    var moviedetailResult: MutableLiveData<MovieDetail> = MutableLiveData()

    lateinit var disposableObserver: DisposableObserver<MovieDetail>

    fun moviedetailResult(): LiveData<MovieDetail> {
        return moviedetailResult
    }

    fun getMovieDetail(movieId: Int) {
        disposableObserver = object: DisposableObserver<MovieDetail>() {
            override fun onComplete() {
                vmLoader.postValue(false)
            }

            override fun onNext(t: MovieDetail) {
                moviedetailResult.postValue(t)
            }

            override fun onError(e: Throwable) {
                vmLoader.postValue(false)
                vmError.postValue(e.message)
            }
        }

        movieRepository.getMovieDetail(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(disposableObserver)
    }
}