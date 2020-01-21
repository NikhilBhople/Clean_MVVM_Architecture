package nikhil.bhople.gojektest.ui.repository

import androidx.lifecycle.LiveData
import nikhil.bhople.gojektest.data.model.NetworkState
import nikhil.bhople.gojektest.data.model.RepoResponse

class TrendingRepoRepositoryImpl : TrendingRepoRepository {
    override fun getTrendingRepos(): LiveData<List<RepoResponse>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getNetworkState(): LiveData<NetworkState> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}