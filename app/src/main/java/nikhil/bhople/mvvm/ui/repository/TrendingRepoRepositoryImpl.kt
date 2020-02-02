package nikhil.bhople.mvvm.ui.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import nikhil.bhople.mvvm.data.interactor.LocalDataSource
import nikhil.bhople.mvvm.data.interactor.NetworkDataSource
import nikhil.bhople.mvvm.data.model.NetworkState
import nikhil.bhople.mvvm.data.model.RepoResponse

class TrendingRepoRepositoryImpl(
    private val localDS: LocalDataSource,
    private val networkDS: NetworkDataSource
) : TrendingRepoRepository {

    private val repoMediatorLiveData = MediatorLiveData<List<RepoResponse>>()
    override val trendingRepos: LiveData<List<RepoResponse>>
        get() = repoMediatorLiveData

    private val networkMediatorLiveData = MediatorLiveData<NetworkState>()
    override val networkState: LiveData<NetworkState>
        get() = networkMediatorLiveData

    private val apiNetworkState = networkDS.networkState
    private val dbNetworkState = localDS.networkState
    private val networkRepo = networkDS.trendingRepo
    private val localRepo = localDS.trendingRepo

    init {
        networkMediatorLiveData.addSource(apiNetworkState) {
            networkMediatorLiveData.postValue(it)
        }
        networkMediatorLiveData.addSource(dbNetworkState) {
            networkMediatorLiveData.postValue(it)
        }
        repoMediatorLiveData.addSource(networkRepo) {
            repoMediatorLiveData.postValue(it)
        }
        repoMediatorLiveData.addSource(localRepo) {
            repoMediatorLiveData.postValue(it)
        }
    }

    override fun getTrendingReposList() {
        if (isNetworkCallNeeded()) {
            fetchDataFromNetwork()
        } else {
            Log.e("NIK", "getting from local")
            localDS.getLocalData()
        }
    }

    override fun fetchDataFromNetwork() {
        Log.e("NIK", "getting from network")
        networkDS.fetchRepos()

    }

    // Refresh data if it's 20 min old
    private fun isNetworkCallNeeded(): Boolean {
        val current = System.currentTimeMillis()
        val storedTime = localDS.getFirstItem()
        return if (storedTime == null) return true else (current.minus(storedTime.updateTime) > 7.2e+6)
    }

}