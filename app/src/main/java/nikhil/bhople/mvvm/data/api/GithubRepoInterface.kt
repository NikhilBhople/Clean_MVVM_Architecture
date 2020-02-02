package nikhil.bhople.mvvm.data.api

import io.reactivex.Observable
import nikhil.bhople.mvvm.data.model.RepoResponse
import retrofit2.http.GET

interface GithubRepoInterface {

    @GET("/repositories")
    fun fetchTrendingRepo(): Observable<List<RepoResponse>>
}