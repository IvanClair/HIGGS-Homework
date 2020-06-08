package personal.ivan.higgshomework.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import kotlinx.coroutines.CoroutineScope
import personal.ivan.higgshomework.binding_model.UserDetailsPageBindingModel
import personal.ivan.higgshomework.binding_model.UserSummaryVhBindingModel
import personal.ivan.higgshomework.io.db.GitHubUserDetailsDao
import personal.ivan.higgshomework.io.model.GitHubUserDetails
import personal.ivan.higgshomework.io.model.IoStatus
import personal.ivan.higgshomework.io.network.GitHubService
import personal.ivan.higgshomework.io.util.IoUtil
import javax.inject.Inject

class GitHubRepository @Inject constructor(
    private val service: GitHubService,
    private val pagedListConfig: PagedList.Config,
    private val userDetailsDao: GitHubUserDetailsDao
) {

    // Constant
    companion object {
        const val PAGE_LIMIT = 180
        const val PREFETCH_DISTANCE = 5
    }

    // region GitHub User Summary

    /**
     * Get user list by paging
     *
     * @param scope    the assigned [CoroutineScope]
     * @param ioStatus live data for listen IO status
     */
    fun getUserPagedList(
        scope: CoroutineScope,
        ioStatus: MutableLiveData<IoStatus>
    ): LiveData<PagedList<UserSummaryVhBindingModel>> {
        val factory = GitHubUserListDataSourceFactory(
            service = service,
            scope = scope,
            ioStatus = ioStatus
        )
        return factory.toLiveData(pagedListConfig)
    }

    /**
     * Search users by paging
     *
     * @param scope    the assigned [CoroutineScope]
     * @param ioStatus live data for listen IO status
     * @param query    keyword
     */
    fun getSearchUsersPagedList(
        scope: CoroutineScope,
        ioStatus: MutableLiveData<IoStatus>,
        query: String
    ): LiveData<PagedList<UserSummaryVhBindingModel>> {
        val factory = GitHubSearchUsersDataSourceFactory(
            service = service,
            scope = scope,
            ioStatus = ioStatus,
            query = query
        )
        return factory.toLiveData(pagedListConfig)
    }

    // endregion

    // region User Details

    /**
     * Ger user detailed information by [username]
     */
    fun getUserDetails(
        username: String,
        ioStatus: MutableLiveData<IoStatus>
    ): LiveData<UserDetailsPageBindingModel> =
        object : IoUtil<GitHubUserDetails, UserDetailsPageBindingModel>(ioStatus = ioStatus) {
            override suspend fun loadFromDb(): GitHubUserDetails? =
                userDetailsDao.load(username = username)

            override suspend fun loadFromNetwork(): GitHubUserDetails? =
                service.getUserDetails(username = username)

            override suspend fun convertFromSource(source: GitHubUserDetails?): UserDetailsPageBindingModel? =
                source?.let { UserDetailsPageBindingModel(data = it) }

            override suspend fun saveToDb(data: GitHubUserDetails) {
                userDetailsDao.insertAll(data = data)
            }

        }.getLiveData()

    // endregion
}