package edmundo.tmdb.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat

object CommonUtils {

    fun setupImg(imageView: ImageView, pathImage: String) {
        Picasso.get().load("""${Constants.API_IMAGES_BASE_URL}$pathImage""")
            .fit()
            .into(imageView)
    }

    fun returnReleaseDate(dateString: String) : String {
        val date = SimpleDateFormat("yyyy-MM-dd").parse(dateString)
        val format = SimpleDateFormat("dd/MM/yyyy")
        val releaseDate = format.format(date)

        return "Lançamento: $releaseDate"
    }
}