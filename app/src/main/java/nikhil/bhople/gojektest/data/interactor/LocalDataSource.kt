package nikhil.bhople.gojektest.data.interactor

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import nikhil.bhople.gojektest.data.db.RepoDao
import nikhil.bhople.gojektest.data.model.RepoResponse

class LocalDataSource(
    private val repoDao: RepoDao,
    private val disposable: CompositeDisposable
) {

    private val _trendingRepos = MutableLiveData<List<RepoResponse>>()
    val trendingRepo: LiveData<List<RepoResponse>>
        get() = _trendingRepos

    fun saveData(list: List<RepoResponse>?) {
        list?.mapIndexed { index, response ->
            response.id = index
            response.updateTime = System.currentTimeMillis()
        }
        repoDao.deleteAll()
        repoDao.insertAll(list!!)
        Log.e("NIK", "data inserted Success")
    }

    fun getLocalData() {
        disposable.add(
            Observable.just(repoDao)
                .subscribeOn(Schedulers.io())
                .subscribe {
                    val list = it.getAll()
                    _trendingRepos.postValue(list)
                }
        )
    }

    fun getFirstItem(): RepoResponse {
        return repoDao.findById(0)
    }

    fun onDestroy() {
        disposable.dispose()
    }
}