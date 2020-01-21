package nikhil.bhople.gojektest.ui.repository

import androidx.lifecycle.LiveData
import io.reactivex.disposables.CompositeDisposable
import nikhil.bhople.gojektest.data.model.NetworkState
import nikhil.bhople.gojektest.data.model.RepoResponse

interface TrendingRepoRepository {
    fun getTrendingRepos () : LiveData<List<RepoResponse>>
    fun getNetworkState(): LiveData<NetworkState>
    fun onDestroy()
}