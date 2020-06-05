package personal.ivan.higgshomework.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import kotlinx.coroutines.CoroutineScope
import personal.ivan.higgshomework.binding_model.UserSummaryVhBindingModel
import personal.ivan.higgshomework.io.model.GitHubUserDetails
import personal.ivan.higgshomework.io.model.GitHubUserSummary
import personal.ivan.higgshomework.io.model.IoRqStatusModel
import personal.ivan.higgshomework.io.network.GitHubService
import personal.ivan.higgshomework.io.util.IoUtil
import javax.inject.Inject

class GitHubRepository @Inject constructor(private val service: GitHubService) {

    // Constant
    companion object {
        const val PAGE_LIMIT = 180
        const val PREFETCH_DISTANCE = 5
    }

    /**
     * Get user list by paging
     *
     * @param scope    the assigned [CoroutineScope]
     * @param ioStatus live data for listen IO status
     */
    fun getUserPagedList(
        scope: CoroutineScope,
        ioStatus: MutableLiveData<IoRqStatusModel<List<GitHubUserSummary>>>
    ): LiveData<PagedList<UserSummaryVhBindingModel>> {
        // create user list data source factory
        val factory = GitHubUserListDataSourceFactory(
            service = service,
            scope = scope,
            ioStatus = ioStatus
        )

        // pagination config
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PAGE_LIMIT)
            .setPrefetchDistance(PREFETCH_DISTANCE)
            .build()

        return factory.toLiveData(config)
    }

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