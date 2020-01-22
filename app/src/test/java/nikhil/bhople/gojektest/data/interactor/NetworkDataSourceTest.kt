package nikhil.bhople.gojektest.data.interactor

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.anyArray
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import nikhil.bhople.gojektest.data.api.GithubRepoInterface
import nikhil.bhople.gojektest.data.model.RepoResponse
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule

class NetworkDataSourceTest {

    private val trendingList : List<RepoResponse> = listOf()

    val apiService = mock<GithubRepoInterface>()

    @Test
    fun getNetworkState() {
    }

    @Test
    fun getTrendingRepo() {
    }

    @Test
    fun fetchRepos() {
        whenever(apiService.fetchTrendingRepo()).thenReturn(any())
    }

    @Test
    fun onDestroy() {
    }
}