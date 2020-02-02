package nikhil.bhople.mvvm.data.interactor

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import nikhil.bhople.mvvm.data.db.RepoDao
import nikhil.bhople.mvvm.data.model.NetworkState
import nikhil.bhople.mvvm.data.model.RepoResponse
import java.util.concurrent.TimeUnit

class LocalDataSource(
    private val repoDao: RepoDao
) {

    private val _trendingRepos = MutableLiveData<List<RepoResponse>>()
    val trendingRepo: LiveData<List<RepoResponse>>
        get() = _trendingRepos

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    fun saveData(list: List<RepoResponse>?) {
        list?.mapIndexed { index, response ->
            response.id = index
            response.updateTime = System.currentTimeMillis()
        }
        repoDao.deleteAll()
        repoDao.insertAll(list!!)
        Log.e("NIK", "data inserted Success")
    }

    @SuppressLint("CheckResult")
    fun getLocalData() {
        Observable.just(repoDao)
            .subscribeOn(Schedulers.io())
            .delay(1, TimeUnit.SECONDS)
            .subscribe {
                _networkState.postValue(NetworkState.LOADING)
                val list = it.getAll()
                _networkState.postValue(NetworkState.LOADED)
                _trendingRepos.postValue(list)
            }
    }

    fun getFirstItem(): RepoResponse {
        return repoDao.findById(0)
    }
}