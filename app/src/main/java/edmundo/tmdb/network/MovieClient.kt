package edmundo.tmdb.network

import com.google.gson.JsonObject
import edmundo.tmdb.model.MovieDetail
import edmundo.tmdb.model.Result
import edmundo.tmdb.model.UpcomingMovie
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieClient {

    @GET("/3/movie/upcoming/")
    fun getUpcomingMovies(@Query("api_key") api_key: String,
                          @Query("language") language: String,
                          @Query("page") page: Int): Single<UpcomingMovie>

    @GET("/3/movie/{movie_id}")
    fun getMovieDetail(@Path("movie_id") movie_id: Int,
                       @Query("api_key") api_key: String,
                        @Query("language") language: String): Single<MovieDetail>

}