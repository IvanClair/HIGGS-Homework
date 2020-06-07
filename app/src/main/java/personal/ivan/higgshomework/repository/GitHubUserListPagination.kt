package personal.ivan.higgshomework.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import personal.ivan.higgshomework.binding_model.UserSummaryVhBindingModel
import personal.ivan.higgshomework.io.model.IoStatus
import personal.ivan.higgshomework.io.network.GitHubService

/**
 * Factory to create [GitHubUserListDataSource]
 */
class GitHubUserListDataSourceFactory(
    private val service: GitHubService,
    private val scope: CoroutineScope,
    private val ioStatus: MutableLiveData<IoStatus>
) : DataSource.Factory<Int, UserSummaryVhBindingModel>() {

    /*
        Override
     */
    override fun create(): DataSource<Int, UserSummaryVhBindingModel> =
        GitHubUserListDataSource(
            service = service,
            scope = scope,
            ioStatus = ioStatus
        )
}

/**
 * Data source of [UserSummaryVhBindingModel] for pagination
 */
class GitHubUserListDataSource(
    private val service: GitHubService,
    private val scope: CoroutineScope,
    private val ioStatus: MutableLiveData<IoStatus>
) : PageKeyedDataSource<Int, UserSummaryVhBindingModel>() {

    /*
        Override
     */
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, UserSummaryVhBindingModel>
    ) {
        loadFromIo(
            index = 0,
            callback = { dataList, nextIndex ->
                callback.onResult(dataList, null, nextIndex)
            })
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, UserSummaryVhBindingModel>
    ) {
        loadFromIo(
            index = params.key,
            callback = { dataList, nextIndex ->
                callback.onResult(dataList, nextIndex)
            })
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, UserSummaryVhBindingModel>
    ) {
        // no need
    }

    /**
     * Load GitHub user list
     */
    private inline fun loadFromIo(
        index: Int,
        crossinline callback: (List<UserSummaryVhBindingModel>, Int) -> Unit
    ) {
        scope.launch(Dispatchers.IO) {
            try {
                ioStatus.postValue(IoStatus.loading())
                val dataList = service.getUserList(since = index)
                callback.invoke(
                    dataList.map { UserSummaryVhBindingModel(data = it) },
                    index + GitHubRepository.PAGE_LIMIT
                )
                ioStatus.postValue(IoStatus.success())
            } catch (e: Exception) {
                // todo handle error code
                ioStatus.postValue(IoStatus.fail())
            }
        }
    }
}
