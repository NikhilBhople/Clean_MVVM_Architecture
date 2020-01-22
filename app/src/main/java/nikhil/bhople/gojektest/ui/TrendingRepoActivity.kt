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
    }

    private fun handlePullToRefresh() {
        swipe_container.setOnRefreshListener {
            viewModel.fetchDataFromNetwork().observe(this, Observer {
                swipe_container.isRefreshing = false
                resetAdapter(it)
            })
        }
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
            val idd = it
            layout_error.visibility = if (it.status == Status.FAILED) View.VISIBLE else View.GONE
            Log.e("NIK", "" + it.msg)
        })
    }

    private fun resetAdapter(it: List<RepoResponse>) {
        list.clear()
        list.addAll(it)
        parent_shimmer_layout.stopShimmerAnimation()
        Log.e("NIK", "Success " + it.size)
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
                    parent_shimmer_layout.startShimmerAnimation()
                    viewModel.filterList(true, list)
                }
                Toast.makeText(this, "you ckucje", Toast.LENGTH_SHORT).show()
            }
            R.id.menuSortByNames -> {
                if (list.isNotEmpty()) {
                    parent_shimmer_layout.startShimmerAnimation()
                    viewModel.filterList(false, list)
                }
                Toast.makeText(this, "you ckucje", Toast.LENGTH_SHORT).show()
            }

        }
        return true
    }


    override fun onStop() {
        super.onStop()
        parent_shimmer_layout.stopShimmerAnimation()
    }

}
