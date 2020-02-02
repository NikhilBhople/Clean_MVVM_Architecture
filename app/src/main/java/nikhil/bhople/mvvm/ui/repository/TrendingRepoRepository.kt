package nikhil.bhople.mvvm.ui.repository

import androidx.lifecycle.LiveData
import nikhil.bhople.mvvm.data.model.NetworkState
import nikhil.bhople.mvvm.data.model.RepoResponse

interface TrendingRepoRepository {
    fun getTrendingReposList ()
    fun fetchDataFromNetwork ()
    val trendingRepos: LiveData<List<RepoResponse>>
    val networkState: LiveData<NetworkState>
}