package nikhil.bhople.gojektest.ui.repository

import android.util.Log
import androidx.lifecycle.LiveData
import nikhil.bhople.gojektest.data.interactor.LocalDataSource
import nikhil.bhople.gojektest.data.interactor.NetworkDataSource
import nikhil.bhople.gojektest.data.model.NetworkState
import nikhil.bhople.gojektest.data.model.RepoResponse

class TrendingRepoRepositoryImpl(
    private val localDS: LocalDataSource,
    private val networkDS: NetworkDataSource
) : TrendingRepoRepository {

    override fun getTrendingRepos(): LiveData<List<RepoResponse>> {
        return if (isNetworkCallNeeded()) {
            Log.e("NIK", "getting from server")
            networkDS.fetchRepos()
            networkDS.trendingRepo
        }else{
            Log.e("NIK", "getting from local")
            localDS.getLocalData()
            localDS.trendingRepo
        }
    }

    override fun fetchDataFromNetwork(): LiveData<List<RepoResponse>> {
        networkDS.fetchRepos()
        return networkDS.trendingRepo
    }

    override fun getNetworkState(): LiveData<NetworkState> {
        return networkDS.networkState
    }

    private fun isNetworkCallNeeded(): Boolean {
        val current = System.currentTimeMillis()
        val storedTime = localDS.getFirstItem()
        return if (storedTime == null) return true else (current.minus(storedTime.updateTime) > 7.2e+6)
    }

    override fun onDestroy() {
        networkDS.onDestroy()
        localDS.onDestroy()
    }
}