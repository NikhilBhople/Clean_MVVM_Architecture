package nikhil.bhople.gojektest.data.api

import io.reactivex.Observable
import nikhil.bhople.gojektest.data.model.RepoResponse
import retrofit2.http.GET

interface GithubRepoInterface {

    @GET("/list")
    fun fetchTrendingRepo(): Observable<List<RepoResponse>>
}