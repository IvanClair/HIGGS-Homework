package personal.ivan.higgshomework.io.network

import personal.ivan.higgshomework.io.model.GitHubUserSummary
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {

    /**
     * Get user list start from assigned index
     *
     * @param since index
     */
    @GET("users")
    suspend fun getUserList(@Query("since") since: Int): List<GitHubUserSummary>

    /**
     * Search users whose name contains [query]
     *
     * @param query     keyword of username
     * @param page      indicate the current pagination
     * @param pageLimit how many items in a request
     */
    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("page_limit") pageLimit: Int
    ): List<GitHubUserSummary>

    /**
     * Get user details
     *
     * @param username the username
     */
    @GET("users/{username}")
    suspend fun getUserDetails(@Path("username") username: String)
}