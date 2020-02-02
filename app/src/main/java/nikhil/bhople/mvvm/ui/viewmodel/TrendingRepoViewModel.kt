package nikhil.bhople.mvvm.ui.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import nikhil.bhople.mvvm.data.model.NetworkState
import nikhil.bhople.mvvm.data.model.RepoResponse
import nikhil.bhople.mvvm.ui.repository.TrendingRepoRepository

class TrendingRepoViewModel(
    private val repository: TrendingRepoRepository
) : ViewModel() {

    private val reposMediatorLiveData = MediatorLiveData<List<RepoResponse>>()
    val trendingRepo: LiveData<List<RepoResponse>>
        get() = reposMediatorLiveData

    init {
        reposMediatorLiveData.addSource(repository.trendingRepos){
            reposMediatorLiveData.value = it
        }
    }

    fun getTrendingRepos() {
         repository.getTrendingReposList()
    }

    val networkState: LiveData<NetworkState> by lazy {
        repository.networkState
    }

    fun fetchDataFromNetwork() {
        repository.fetchDataFromNetwork()
    }


    @SuppressLint("CheckResult")
    fun filterList(
        isSortByStars: Boolean,
        list: List<RepoResponse>
    ) {
        Observable.just(list)
            .subscribeOn(Schedulers.io())
            .subscribe {
                if (isSortByStars) {
                    val sortedList = it.sortedWith(compareBy { it.stars })
                    reposMediatorLiveData.postValue(sortedList)
                } else {
                    val sortedList = it.sortedWith(compareBy { it.name })
                    reposMediatorLiveData.postValue(sortedList)
                }
            }
    }

}