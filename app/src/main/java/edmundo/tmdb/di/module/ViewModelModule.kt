package edmundo.tmdb.di.module

import android.arch.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import edmundo.tmdb.adapter.UpcomingMoviesAdapter
import edmundo.tmdb.network.MovieRepository
import edmundo.tmdb.viewModel.MovieDetailViewModel
import edmundo.tmdb.viewModel.UpcomingMoviesViewModel
import edmundo.tmdb.viewModel.factory.MovieDetailViewModelFactory
import edmundo.tmdb.viewModel.factory.UpcomingMoviesViewModelFactory

@Module
class ViewModelModule {

    @Provides
    fun upComingViewModel(movieRepository: MovieRepository): UpcomingMoviesViewModel {
        return UpcomingMoviesViewModel(movieRepository)
    }

    @Provides
    fun provideUpcomingMoviesViewModel(upComingViewModel: UpcomingMoviesViewModel): ViewModelProvider.Factory {
        return UpcomingMoviesViewModelFactory(upComingViewModel)
    }

    @Provides
    fun movieDetailViewModel(movieRepository: MovieRepository): MovieDetailViewModel {
        return MovieDetailViewModel(movieRepository)
    }

    @Provides
    fun provideMovieDetailViewModel(movieDetailViewModel: MovieDetailViewModel): ViewModelProvider.Factory {
        return MovieDetailViewModelFactory(movieDetailViewModel)
    }

    @Provides
    fun provideUpcomingMoviesAdapter(): UpcomingMoviesAdapter {
        return UpcomingMoviesAdapter(ArrayList())
    }
}