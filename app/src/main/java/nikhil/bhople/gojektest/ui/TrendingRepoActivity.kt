package nikhil.bhople.gojektest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_trending_repo.*
import nikhil.bhople.gojektest.R
import nikhil.bhople.gojektest.data.model.RepoResponse
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

    private val list = ArrayList<RepoResponse>()
    private val adapter by lazy {
        RecyclerAdapter( list)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trending_repo)

        setUpRecyclerView()
        getData()
    }

    private fun setUpRecyclerView() {
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter
    }

    private fun getData() {
        viewModel.currencyList.observe(this, Observer {
            list.clear()
            list.addAll(it)
            Log.e("NIK","Success "+it.size)
            adapter.notifyDataSetChanged()
        })

        viewModel.networkState.observe(this, Observer {
            val idd = it
            Log.e("NIK",""+it.msg)
        })
    }

}
