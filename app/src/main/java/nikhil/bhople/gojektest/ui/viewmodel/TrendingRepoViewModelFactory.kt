package nikhil.bhople.gojektest.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.disposables.CompositeDisposable
import nikhil.bhople.gojektest.ui.repository.TrendingRepoRepository

class TrendingRepoViewModelFactory(
    private val repository: TrendingRepoRepository,
    private val disposable: CompositeDisposable
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TrendingRepoViewModel(repository, disposable) as T
    }
}