package edmundo.tmdb.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

open class BaseViewModel: ViewModel() {

    var vmLoader: MutableLiveData<Boolean> = MutableLiveData()
    var vmError: MutableLiveData<String> = MutableLiveData()



    fun vmLoader(): LiveData<Boolean> {
        return vmLoader
    }

    fun vmError(): LiveData<String> {
        return vmError
    }


}