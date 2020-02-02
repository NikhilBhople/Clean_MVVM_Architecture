package nikhil.bhople.mvvm.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_trending_repo.*
import kotlinx.android.synthetic.main.error_layout.*
import nikhil.bhople.mvvm.R
import nikhil.bhople.mvvm.data.model.NetworkState
import nikhil.bhople.mvvm.data.model.Status
import nikhil.bhople.mvvm.ui.viewmodel.TrendingRepoViewModel
import nikhil.bhople.mvvm.ui.viewmodel.TrendingRepoViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance


class TrendingRepoActivity : AppCompatActivity(), KodeinAware {
    override val kodein: Kodein by closestKodein()

    private val factory: TrendingRepoViewModelFactory by instance()
    private val viewModel: TrendingRepoViewModel by lazy {
        ViewModelProviders.of(this, factory).get(TrendingRepoViewModel::class.java)
    }
    private val adapter: RecyclerAdapter by instance()
    private var networkState: NetworkState = NetworkState.LOADED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trending_repo)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        parent_shimmer_layout.startShimmerAnimation()
        setUpRecyclerView()
        getData()
        handlePullToRefresh()
        handleRetry()
    }

    private fun setUpRecyclerView() {
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter
    }

    private fun getData() {
        viewModel.trendingRepo.observe(this, Observer {
            if (it != null) adapter.resetData(it)
        })

        viewModel.networkState.observe(this, Observer {
            networkState = it
            if (it.status == Status.FAILED){
                handleLayout(View.GONE, View.GONE, View.VISIBLE)
            }else if (it.status == Status.SUCCESS){
                handleLayout(View.VISIBLE, View.GONE, View.GONE)
            }
            Log.e("NIKA", "" + it.msg)
        })

        viewModel.getTrendingRepos()
    }

    private fun handleRetry() {
        button_retry.setOnClickListener {
            handleLayout(View.GONE, View.VISIBLE, View.GONE)
            parent_shimmer_layout.startShimmerAnimation()
            fetchFreshData()
        }
    }

    private fun handleLayout(swipeContainer: Int, shimmerLayout: Int, error: Int) {
        parent_shimmer_layout.stopShimmerAnimation()
        swipe_container.isRefreshing = false
        swipe_container.visibility = swipeContainer
        parent_shimmer_layout.visibility = shimmerLayout
        layout_error.visibility = error
    }

    private fun handlePullToRefresh() {
        swipe_container.setOnRefreshListener(this::fetchFreshData)
    }

    private fun fetchFreshData() {
        if (networkState.status !=  Status.RUNNING) viewModel.fetchDataFromNetwork()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menuSortByStars -> {
                viewModel.filterList(true, adapter.getList())
            }
            R.id.menuSortByNames -> {
                viewModel.filterList(false, adapter.getList())
            }
        }
        return true
    }

    override fun onStop() {
        super.onStop()
        parent_shimmer_layout.stopShimmerAnimation()
    }

}
