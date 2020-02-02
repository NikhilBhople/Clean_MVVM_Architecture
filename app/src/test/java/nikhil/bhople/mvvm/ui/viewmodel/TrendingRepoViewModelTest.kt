package nikhil.bhople.mvvm.ui.viewmodel

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import nikhil.bhople.mvvm.ui.repository.TrendingRepoRepository
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TrendingRepoViewModelTest {

    val repository = mock<TrendingRepoRepository>()
    @Test
    fun getFilterRepo() {
    }

    @Test
    fun getRepoList() {
        whenever(repository.fetchDataFromNetwork()).thenReturn(any())
        assertSame(any(), any())
    }

    @Test
    fun getNetworkState() {
    }

    @Test
    fun fetchDataFromNetwork() {
    }

    @Test
    fun filterList() {
    }
}