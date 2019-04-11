package edmundo.tmdb.viewModel.factory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import edmundo.tmdb.viewModel.UpcomingMoviesViewModel
import javax.inject.Inject

class UpcomingMoviesViewModelFactory @Inject constructor(
    private val upcomingMoviesViewModel: UpcomingMoviesViewModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpcomingMoviesViewModel::class.java)) {
            return upcomingMoviesViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}