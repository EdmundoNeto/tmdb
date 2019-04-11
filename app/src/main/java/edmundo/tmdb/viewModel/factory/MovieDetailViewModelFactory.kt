package edmundo.tmdb.viewModel.factory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import edmundo.tmdb.viewModel.MovieDetailViewModel
import javax.inject.Inject

class MovieDetailViewModelFactory @Inject constructor(private val movieDetailViewModel: MovieDetailViewModel)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {
            return movieDetailViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}