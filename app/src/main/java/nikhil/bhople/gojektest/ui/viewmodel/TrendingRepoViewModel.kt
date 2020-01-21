package nikhil.bhople.gojektest.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import nikhil.bhople.gojektest.data.model.NetworkState
import nikhil.bhople.gojektest.data.model.RepoResponse
import nikhil.bhople.gojektest.ui.repository.TrendingRepoRepository

class TrendingRepoViewModel(
    private val repository: TrendingRepoRepository
) : ViewModel() {

    val currencyList: LiveData<List<RepoResponse>> by lazy {
        repository.getTrendingRepos()
    }

    val networkState: LiveData<NetworkState> by lazy {
        repository.getNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        repository.onDestroy()
    }
}