package nikhil.bhople.gojektest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import nikhil.bhople.gojektest.R
import nikhil.bhople.gojektest.ui.repository.TrendingRepoRepository
import nikhil.bhople.gojektest.ui.viewmodel.TrendingRepoViewModel
import nikhil.bhople.gojektest.ui.viewmodel.TrendingRepoViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class TrendingRepoActivity : AppCompatActivity(), KodeinAware {
    override val kodein: Kodein by closestKodein()

    private val factory: TrendingRepoViewModelFactory by instance()
    private val viewModel:TrendingRepoViewModel by lazy {
        ViewModelProviders.of(this, factory).get(TrendingRepoViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trending_repo)

        getData()
    }

    private fun getData() {
        viewModel.currencyList.observe(this, Observer {
            val idd = it
        })

        viewModel.networkState.observe(this, Observer {
            val idd = it
        })
    }

}
