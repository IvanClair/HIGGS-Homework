package personal.ivan.higgshomework.view_model

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import personal.ivan.higgshomework.binding_model.UserListPageBindingModel
import personal.ivan.higgshomework.io.model.GitHubUserSummary
import personal.ivan.higgshomework.io.model.IoRqStatusModel
import personal.ivan.higgshomework.repository.GitHubRepository
import personal.ivan.higgshomework.ui_utils.UiUtil
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: GitHubRepository,
    val userListPageBindingModel: LiveData<UserListPageBindingModel>
) : ViewModel() {

    // region User List Page

    val userVhModelList: LiveData<IoRqStatusModel<List<GitHubUserSummary>>> =
        repository.getUserList(index = 0)

    /**
     * Update user list page UI widgets visibility
     */
    fun updateUserListUiWidgetsVisibility(enable: Boolean) {
        userListPageBindingModel.value?.updateVisibility(enable = enable)
    }

    /**
     * User clicked an user
     */
    fun userOnClickListener(view: View) {
        if (UiUtil.allowClick()) {

        }
    }

    // endregion
}