package edmundo.tmdb.adapter

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import edmundo.tmdb.model.Result
import edmundo.tmdb.model.UpcomingMovie
import edmundo.tmdb.network.MovieClient
import edmundo.tmdb.network.MovieRepository
import edmundo.tmdb.utils.StateEnum
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UpcomingMoviesDataSource(private val movieRepository: MovieRepository,
                               private val compositeDisposable: CompositeDisposable
): PageKeyedDataSource<Int, Result>() {

    private var retryCompletable: Completable? = null
    var state: MutableLiveData<StateEnum> = MutableLiveData()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Result>) {
        updateState(StateEnum.LOADING)
        compositeDisposable.add(
            movieRepository.getUpcoming(1)
                .subscribe(
                    { response ->
                        updateState(StateEnum.DONE)
                        callback.onResult(response,
                            null,
                            2
                        )
                    },
                    {
                        updateState(StateEnum.ERROR)
                        setRetry(Action { loadInitial(params, callback) })
                    }
                )
        )

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
        updateState(StateEnum.LOADING)
        compositeDisposable.add(
            movieRepository.getUpcoming(params.key)
                .subscribe(
                    { response ->
                        updateState(StateEnum.DONE)
                        callback.onResult(response,
                            params.key + 1
                        )
                    },
                    {
                        updateState(StateEnum.ERROR)
                        setRetry(Action { loadAfter(params, callback) })
                    }
                )
        )
    }

    private fun updateState(state: StateEnum) {
        this.state.postValue(state)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {

    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }
}