package nikhil.bhople.gojektest.ui.repository

import androidx.lifecycle.LiveData
import nikhil.bhople.gojektest.data.interactor.LocalDataSource
import nikhil.bhople.gojektest.data.interactor.NetworkDataSource
import nikhil.bhople.gojektest.data.model.NetworkState
import nikhil.bhople.gojektest.data.model.RepoResponse

class TrendingRepoRepositoryImpl(
    private val localDS: LocalDataSource,
    private val networkDS: NetworkDataSource
) : TrendingRepoRepository {

    //TODO return local data if available

    override fun getTrendingRepos(): LiveData<List<RepoResponse>> {
        networkDS.fetchRepos()
        return networkDS.trendingRepo
    }

    override fun getNetworkState(): LiveData<NetworkState> {
        return networkDS.networkState
    }

    override fun onDestroy() {
        networkDS.onDestroy()
    }
}