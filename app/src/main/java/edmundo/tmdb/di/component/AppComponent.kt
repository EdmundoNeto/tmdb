package edmundo.tmdb.di.component

import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import edmundo.tmdb.TmdbApplication
import edmundo.tmdb.di.module.ActivityProvider
import edmundo.tmdb.di.module.AppModule
import edmundo.tmdb.di.module.NetworkModule
import edmundo.tmdb.di.module.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        AndroidSupportInjectionModule::class, AppModule::class,
        NetworkModule::class, ActivityProvider::class, ViewModelModule::class)
)
interface AppComponent {
    fun inject(app: TmdbApplication)
}