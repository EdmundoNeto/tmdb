package edmundo.tmdb.utils

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import edmundo.tmdb.adapter.UpcomingMoviesAdapter
import edmundo.tmdb.model.Genre
import edmundo.tmdb.model.Result
import java.text.SimpleDateFormat

object BindingUtils {

    @JvmStatic
    @BindingAdapter("adapterMovieItem")
    fun adapterMovieItem(recyclerView: RecyclerView, movieItens: List<Result>) {
        val adapter = recyclerView.adapter as UpcomingMoviesAdapter?
        if (adapter != null) {
            adapter.clearItems()
            adapter.addItems(movieItens)
        }
    }

    @JvmStatic
    @BindingAdapter("backgroundImage")
    fun backgroundImage(image: ImageView, path: String?) {
        if(path != null)
            Picasso.get().load("""${Constants.API_IMAGES_BASE_URL}$path""")
                .fit()
                .into(image)
    }

    @JvmStatic
    @BindingAdapter("returnReleaseDate")
    fun returnReleaseDate(textView: TextView, dateString: String?) {
        if(dateString != null ) {
            val date = SimpleDateFormat("yyyy-MM-dd").parse(dateString)
            val format = SimpleDateFormat("dd/MM/yyyy")
            val releaseDate = format.format(date)

            textView.text = "Lançamento: $releaseDate"
        }
    }

    @JvmStatic
    @BindingAdapter("setGenres")
    fun setGenres(textView: TextView, genres: List<Genre>?) {
        genres?.forEach {
            textView.text = """${textView.text}${it.name}, """
        }
    }

}