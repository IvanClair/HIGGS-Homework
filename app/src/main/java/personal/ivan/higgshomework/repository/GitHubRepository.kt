package personal.ivan.higgshomework.repository

import androidx.lifecycle.LiveData
import personal.ivan.higgshomework.io.model.GitHubUserDetails
import personal.ivan.higgshomework.io.model.GitHubUserSummary
import personal.ivan.higgshomework.io.model.IoRqStatusModel
import personal.ivan.higgshomework.io.network.GitHubService
import personal.ivan.higgshomework.io.util.IoUtil
import javax.inject.Inject

class GitHubRepository @Inject constructor(private val service: GitHubService) {

    // Constant
    companion object {
        const val PAGE_LIMIT = 30
    }

    /**
     * Get user list start from assigned index
     */
    fun getUserList(index: Int): LiveData<IoRqStatusModel<List<GitHubUserSummary>>> =
        object : IoUtil<List<GitHubUserSummary>, List<GitHubUserSummary>>() {
            override suspend fun loadFromDb(): List<GitHubUserSummary>? = null

            override suspend fun loadFromNetwork(): List<GitHubUserSummary>? =
                service.getUserList(since = index)

            override suspend fun convertFromSource(source: List<GitHubUserSummary>?): List<GitHubUserSummary>? =
                source

            override suspend fun saveToDb(data: List<GitHubUserSummary>) {}

        }.getLiveData()

    /**
     * Search users by [keyword]
     *
     * @param page indicate current pagination
     */
    fun searchUsers(
        keyword: String,
        page: Int
    ): LiveData<IoRqStatusModel<List<GitHubUserSummary>>> =
        object : IoUtil<List<GitHubUserSummary>, List<GitHubUserSummary>>() {
            override suspend fun loadFromDb(): List<GitHubUserSummary>? = null

            override suspend fun loadFromNetwork(): List<GitHubUserSummary>? =
                service.searchUsers(
                    query = keyword,
                    page = page,
                    pageLimit = PAGE_LIMIT
                )

            override suspend fun convertFromSource(source: List<GitHubUserSummary>?): List<GitHubUserSummary>? =
                source

            override suspend fun saveToDb(data: List<GitHubUserSummary>) {}

        }.getLiveData()

    /**
     * Ger user detailed information by [username]
     */
    fun getUserDetails(username: String): LiveData<IoRqStatusModel<GitHubUserDetails>> =
        object : IoUtil<GitHubUserDetails, GitHubUserDetails>() {
            override suspend fun loadFromDb(): GitHubUserDetails? = null

            override suspend fun loadFromNetwork(): GitHubUserDetails? =
                service.getUserDetails(username = username)

            override suspend fun convertFromSource(source: GitHubUserDetails?): GitHubUserDetails? =
                source

            override suspend fun saveToDb(data: GitHubUserDetails) {}

        }.getLiveData()
}