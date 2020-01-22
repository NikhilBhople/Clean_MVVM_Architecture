package nikhil.bhople.gojektest.application

import android.app.Application
import io.reactivex.disposables.CompositeDisposable
import nikhil.bhople.gojektest.data.api.GithubRepoInterface
import nikhil.bhople.gojektest.data.api.GithubRepoRestClient
import nikhil.bhople.gojektest.data.db.RepoDatabase
import nikhil.bhople.gojektest.data.interactor.LocalDataSource
import nikhil.bhople.gojektest.data.interactor.NetworkDataSource
import nikhil.bhople.gojektest.ui.repository.TrendingRepoRepository
import nikhil.bhople.gojektest.ui.repository.TrendingRepoRepositoryImpl
import nikhil.bhople.gojektest.ui.viewmodel.TrendingRepoViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class GojekApplication: Application(), KodeinAware  {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@GojekApplication))

        bind<GithubRepoInterface>() with singleton { GithubRepoRestClient.getClient() }
        bind() from provider { CompositeDisposable() }

        bind() from singleton { RepoDatabase(instance()) }
        bind() from singleton { instance<RepoDatabase>().repoDao() }
        bind() from singleton { LocalDataSource(instance(), instance()) }

        bind<NetworkDataSource>() with singleton { NetworkDataSource(instance(), instance(), instance()) }
        bind<TrendingRepoRepository>() with singleton { TrendingRepoRepositoryImpl(instance(), instance()) }
        bind<TrendingRepoViewModelFactory>() with provider { TrendingRepoViewModelFactory(instance(), instance()) }
    }
}