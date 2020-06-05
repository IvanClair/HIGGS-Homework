package personal.ivan.higgshomework.view_model

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import personal.ivan.higgshomework.binding_model.UserListPageBindingModel
import personal.ivan.higgshomework.io.model.GitHubUserSummary
import personal.ivan.higgshomework.io.model.IoRqStatusModel
import personal.ivan.higgshomework.repository.GitHubRepository
import personal.ivan.higgshomework.ui_utils.UiUtil
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: GitHubRepository) : ViewModel() {

    // region User List Page

    // page binding model
    val userListPageBindingModel: LiveData<UserListPageBindingModel> =
        MutableLiveData<UserListPageBindingModel>().apply {
            value = UserListPageBindingModel()
        }

    // get user list IO status
    val getUserIoStatus: MutableLiveData<IoRqStatusModel<List<GitHubUserSummary>>> =
        MutableLiveData()

    // user list data list from IO
    val getUserPagedList: LiveData<PagedList<GitHubUserSummary>> =
        repository.getUserPagedList(scope = viewModelScope, ioStatus = getUserIoStatus)

    /**
     * Update user list page UI widgets visibility
     */
    fun updateUserListUiWidgetsVisibility(loading: Boolean) {
        userListPageBindingModel.value?.updateVisibility(
            enableLoading = loading,
            enableSearch = !loading
        )
    }

    /**
     * User clicked an user
     */
    fun userOnClickListener(view: View, model: GitHubUserSummary) {
        if (UiUtil.allowClick()) {

        }
    }

    // endregion
}