package nikhil.bhople.gojektest.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_trending_repo.*
import kotlinx.android.synthetic.main.error_layout.*
import nikhil.bhople.gojektest.R
import nikhil.bhople.gojektest.data.model.NetworkState
import nikhil.bhople.gojektest.data.model.RepoResponse
import nikhil.bhople.gojektest.data.model.Status
import nikhil.bhople.gojektest.ui.viewmodel.TrendingRepoViewModel
import nikhil.bhople.gojektest.ui.viewmodel.TrendingRepoViewModelFactory
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

    private var networkState: NetworkState = NetworkState.LOADED
    private val list = ArrayList<RepoResponse>()
    private val adapter by lazy {
        RecyclerAdapter(list, this)
    }

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
        viewModel.repoList.observe(this, Observer {
            resetAdapter(it)
        })

        viewModel.filterRepo.observe(this, Observer {
            resetAdapter(it)

        })

        viewModel.networkState.observe(this, Observer {
            networkState = it
            if (it.status == Status.FAILED){
                parent_shimmer_layout.stopShimmerAnimation()
                swipe_container.isRefreshing = false
                handleLayout(View.GONE, View.GONE, View.VISIBLE)
            }else if (it.status == Status.SUCCESS){
                parent_shimmer_layout.stopShimmerAnimation()
                swipe_container.isRefreshing = false
                handleLayout(View.VISIBLE, View.GONE, View.GONE)
            }
            Log.e("NIK", "" + it.msg)
        })
    }

    private fun handleRetry() {
        button_retry.setOnClickListener {
            handleLayout(View.GONE, View.VISIBLE, View.GONE)
            parent_shimmer_layout.startShimmerAnimation()
            fetchFreshData()
        }
    }

    private fun handleLayout(swipeContainer: Int, shimmerLayout: Int, error: Int) {
        swipe_container.visibility = swipeContainer
        parent_shimmer_layout.visibility = shimmerLayout
        layout_error.visibility = error
    }

    private fun handlePullToRefresh() {
        swipe_container.setOnRefreshListener {
            fetchFreshData()
        }
    }

    private fun fetchFreshData() {
        if (networkState.status !=  Status.RUNNING) {
            viewModel.fetchDataFromNetwork().observe(this, Observer {
                swipe_container.isRefreshing = false
                resetAdapter(it)
            })
        }
    }

    private fun resetAdapter(it: List<RepoResponse>) {
        list.clear()
        list.addAll(it)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menuSortByStars -> {
                if (list.isNotEmpty()) {
                    viewModel.filterList(true, list)
                }
            }
            R.id.menuSortByNames -> {
                if (list.isNotEmpty()) {
                    viewModel.filterList(false, list)
                }
            }
        }
        return true
    }

    override fun onStop() {
        super.onStop()
        parent_shimmer_layout.stopShimmerAnimation()
    }

}
