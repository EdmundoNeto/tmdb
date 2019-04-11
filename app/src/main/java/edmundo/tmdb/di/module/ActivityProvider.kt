package edmundo.tmdb.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import edmundo.tmdb.MainActivity
import edmundo.tmdb.MovieDetailActivity

@Module
abstract class ActivityProvider {
    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun MainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun MovieDetailActivity(): MovieDetailActivity


}