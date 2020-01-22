package nikhil.bhople.gojektest.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import nikhil.bhople.gojektest.data.model.NetworkState
import nikhil.bhople.gojektest.data.model.RepoResponse
import nikhil.bhople.gojektest.ui.repository.TrendingRepoRepository

class TrendingRepoViewModel(
    private val repository: TrendingRepoRepository,
    private val disposable: CompositeDisposable
) : ViewModel() {

    private val _filterRepos = MutableLiveData<List<RepoResponse>>()
    val filterRepo: LiveData<List<RepoResponse>>
        get() = _filterRepos

    val repoList: LiveData<List<RepoResponse>> by lazy {
        repository.getTrendingRepos()
    }

    val networkState: LiveData<NetworkState> by lazy {
        repository.getNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        repository.onDestroy()
        disposable.dispose()
    }

    fun filterList(
        isSortByStars: Boolean,
        list: ArrayList<RepoResponse>
    ) {
        disposable.add(
            Observable.just(list)
                .subscribeOn(Schedulers.io())
                .subscribe {
                    if (isSortByStars) {
                        var sortedList = list.sortedWith(compareBy({ it.stars }))
                        _filterRepos.postValue(sortedList)
                    } else {
                        var sortedList = list.sortedWith(compareBy({ it.name }))
                        _filterRepos.postValue(sortedList)
                    }
                }
        )
    }
}