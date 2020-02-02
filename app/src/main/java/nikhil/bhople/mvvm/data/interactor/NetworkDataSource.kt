package nikhil.bhople.mvvm.data.interactor

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.schedulers.Schedulers
import nikhil.bhople.mvvm.data.api.GithubRepoInterface
import nikhil.bhople.mvvm.data.model.NetworkState
import nikhil.bhople.mvvm.data.model.RepoResponse

class NetworkDataSource(
    private val apiService: GithubRepoInterface,
    private val localDS: LocalDataSource
) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _trendingRepos = MutableLiveData<List<RepoResponse>>()
    val trendingRepo: LiveData<List<RepoResponse>>
        get() = _trendingRepos

    @SuppressLint("CheckResult")
    fun fetchRepos() {
        try {
            _networkState.postValue(NetworkState.LOADING)
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
                        Log.e("NIK", it.message)
                    }
                )

        } catch (e: Exception) {
            _networkState.postValue(NetworkState.ERROR)
            Log.e("NIK", e.message)
        }
    }
}