package personal.ivan.higgshomework.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import kotlinx.coroutines.CoroutineScope
import personal.ivan.higgshomework.binding_model.UserDetailsPageBindingModel
import personal.ivan.higgshomework.binding_model.UserSummaryVhBindingModel
import personal.ivan.higgshomework.io.model.GitHubUserDetails
import personal.ivan.higgshomework.io.model.IoStatus
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
        ioStatus: MutableLiveData<IoStatus>
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
     * Ger user detailed information by [username]
     */
    fun getUserDetails(
        username: String,
        ioStatus: MutableLiveData<IoStatus>
    ): LiveData<UserDetailsPageBindingModel> =
        object : IoUtil<GitHubUserDetails, UserDetailsPageBindingModel>(ioStatus = ioStatus) {
            override suspend fun loadFromDb(): GitHubUserDetails? = null

            override suspend fun loadFromNetwork(): GitHubUserDetails? =
                service.getUserDetails(username = username)

            override suspend fun convertFromSource(source: GitHubUserDetails?): UserDetailsPageBindingModel? =
                source?.let { UserDetailsPageBindingModel(data = it) }

            override suspend fun saveToDb(data: GitHubUserDetails) {}

        }.getLiveData()
}