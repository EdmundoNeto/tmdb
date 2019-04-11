package edmundo.tmdb.network

import android.graphics.Movie
import com.google.gson.Gson
import com.google.gson.JsonObject
import edmundo.tmdb.model.MovieDetail
import edmundo.tmdb.model.Result
import edmundo.tmdb.model.UpcomingMovie
import edmundo.tmdb.utils.Constants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieClient: MovieClient) {


    fun getUpcoming(page: Int?): Observable<List<Result>> {

        return Observable.create {

            val emitter = it

            movieClient.getUpcomingMovies(Constants.API_KEY, Constants.LANGUAGE, page!!)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<UpcomingMovie>() {
                    override fun onSuccess(t: UpcomingMovie) {
                        if (!emitter.isDisposed){
                            emitter.onNext(t.results)
                        }

                    }

                    override fun onError(e: Throwable) {
                        emitter.onError(e)
                    }
                })
        }
    }

    fun getMovieDetail(movieId: Int): Observable<MovieDetail> {

        return Observable.create {

            val emitter = it

            movieClient.getMovieDetail(movieId, Constants.API_KEY, Constants.LANGUAGE)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieDetail>() {
                    override fun onSuccess(t: MovieDetail) {

                        if (!emitter.isDisposed){
                            emitter.onNext(t)
                        }

                        emitter.onComplete()
                    }

                    override fun onError(e: Throwable) {
                        emitter.onError(e)
                    }
                })
        }
    }

}