package nikhil.bhople.gojektest.data.interactor

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import nikhil.bhople.gojektest.data.api.GithubRepoInterface
import nikhil.bhople.gojektest.data.model.NetworkState
import nikhil.bhople.gojektest.data.model.RepoResponse

class NetworkDataSource(
    private val apiService: GithubRepoInterface,
    private val localDS: LocalDataSource,
    private val disposable: CompositeDisposable
) {
    private val TAG = "NIK"

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _trendingRepos = MutableLiveData<List<RepoResponse>>()
    val trendingRepo: LiveData<List<RepoResponse>>
        get() = _trendingRepos

    fun fetchRepos(){
        try {
            _networkState.postValue(NetworkState.LOADING)
            disposable.add(
                apiService.fetchTrendingRepo()
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _networkState.postValue(NetworkState.LOADED)
                            _trendingRepos.postValue(it)
                            localDS.saveData(it)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e(TAG, it.message)
                        }
                    )
            )

        } catch (e: Exception) {
            _networkState.postValue(NetworkState.ERROR)
            Log.e(TAG, e.message)
        }
    }


    fun onDestroy() {
        disposable.dispose()
    }
}