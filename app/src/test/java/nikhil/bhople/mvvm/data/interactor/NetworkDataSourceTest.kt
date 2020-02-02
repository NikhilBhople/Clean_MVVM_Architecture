package nikhil.bhople.mvvm.data.interactor

import com.nhaarman.mockitokotlin2.mock
import nikhil.bhople.mvvm.data.api.GithubRepoInterface
import org.junit.Test

class NetworkDataSourceTest {

   // private val trendingList : List<RepoResponse> = listOf()

    val apiService = mock<GithubRepoInterface>()

    @Test
    fun getNetworkState() {
    }

    @Test
    fun getTrendingRepo() {
    }

    @Test
    fun fetchRepos() {
      //  whenever(apiService.fetchTrendingRepo()).thenReturn(any())
    }

    @Test
    fun onDestroy() {
    }
}