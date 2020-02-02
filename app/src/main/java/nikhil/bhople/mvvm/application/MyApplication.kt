package nikhil.bhople.mvvm.application

import android.app.Application
import nikhil.bhople.mvvm.data.api.GithubRepoInterface
import nikhil.bhople.mvvm.data.api.GithubRepoRestClient
import nikhil.bhople.mvvm.data.db.RepoDatabase
import nikhil.bhople.mvvm.data.interactor.LocalDataSource
import nikhil.bhople.mvvm.data.interactor.NetworkDataSource
import nikhil.bhople.mvvm.ui.RecyclerAdapter
import nikhil.bhople.mvvm.ui.repository.TrendingRepoRepository
import nikhil.bhople.mvvm.ui.repository.TrendingRepoRepositoryImpl
import nikhil.bhople.mvvm.ui.viewmodel.TrendingRepoViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MyApplication : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@MyApplication))

        bind<GithubRepoInterface>() with singleton { GithubRepoRestClient.getClient() }

        bind() from singleton { RepoDatabase(instance()) }
        bind() from singleton { instance<RepoDatabase>().repoDao() }
        bind() from singleton { LocalDataSource(instance()) }

        bind() from  singleton { NetworkDataSource(instance(), instance()) }
        bind<TrendingRepoRepository>() with singleton { TrendingRepoRepositoryImpl(instance(), instance()) }
        bind() from provider { TrendingRepoViewModelFactory(instance()) }
        bind() from singleton { RecyclerAdapter(ArrayList()) }
    }
}