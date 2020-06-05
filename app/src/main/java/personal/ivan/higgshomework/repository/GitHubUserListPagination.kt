package personal.ivan.higgshomework.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import personal.ivan.higgshomework.io.model.GitHubUserSummary
import personal.ivan.higgshomework.io.model.IoRqStatusModel
import personal.ivan.higgshomework.io.network.GitHubService

/**
 * Factory to create [GitHubUserListDataSource]
 */
class GitHubUserListDataSourceFactory(
    private val service: GitHubService,
    private val scope: CoroutineScope,
    private val ioStatus: MutableLiveData<IoRqStatusModel<List<GitHubUserSummary>>>
) : DataSource.Factory<Int, GitHubUserSummary>() {

    override fun create(): DataSource<Int, GitHubUserSummary> =
        GitHubUserListDataSource(
            service = service,
            scope = scope,
            ioStatus = ioStatus
        )
}

/**
 * Data source of [GitHubUserSummary] for pagination
 */
class GitHubUserListDataSource(
    private val service: GitHubService,
    private val scope: CoroutineScope,
    private val ioStatus: MutableLiveData<IoRqStatusModel<List<GitHubUserSummary>>>
) : PageKeyedDataSource<Int, GitHubUserSummary>() {

    /*
        Override
     */
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GitHubUserSummary>
    ) {
        loadFromIo(
            index = 0,
            callback = { dataList, nextIndex ->
                callback.onResult(dataList, null, nextIndex)
            })
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GitHubUserSummary>
    ) {
        loadFromIo(
            index = params.key,
            callback = { dataList, nextIndex ->
                callback.onResult(dataList, nextIndex)
            })
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GitHubUserSummary>
    ) {
        // no need
    }

    /**
     * Load GitHub user list
     */
    private inline fun loadFromIo(
        index: Int,
        crossinline callback: (List<GitHubUserSummary>, Int) -> Unit
    ) {
        scope.launch(Dispatchers.IO) {
            try {
                ioStatus.postValue(IoRqStatusModel.loading())
                val dataList = service.getUserList(since = index)
                callback.invoke(dataList, index + GitHubRepository.PAGE_LIMIT)
                ioStatus.postValue(IoRqStatusModel.success(data = dataList))
            } catch (e: Exception) {
                ioStatus.postValue(IoRqStatusModel.fail())
            }
        }
    }
}
