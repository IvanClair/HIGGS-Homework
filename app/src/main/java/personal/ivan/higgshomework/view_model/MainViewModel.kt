package personal.ivan.higgshomework.view_model

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagedList
import personal.ivan.higgshomework.binding_model.UserDetailsPageBindingModel
import personal.ivan.higgshomework.binding_model.UserListPageBindingModel
import personal.ivan.higgshomework.binding_model.UserSummaryVhBindingModel
import personal.ivan.higgshomework.io.model.GitHubUserSummary
import personal.ivan.higgshomework.io.model.IoRqStatusModel
import personal.ivan.higgshomework.repository.GitHubRepository
import personal.ivan.higgshomework.ui_utils.UiUtil
import personal.ivan.higgshomework.view.user_list.UserListFragmentDirections
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
    val getUserPagedList: LiveData<PagedList<UserSummaryVhBindingModel>> =
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
    fun userOnClickListener(
        view: View,
        model: UserSummaryVhBindingModel,
        avatar: ImageView
    ) {
        if (UiUtil.allowClick()) {
            view.findNavController().navigate(
                UserListFragmentDirections.navigateToUserDetails(username = model.username),
                FragmentNavigatorExtras(avatar to model.username)
            )
        }
    }

    // endregion

    // region User Details Page

    val userDetailsPageBindingModel: MutableLiveData<UserDetailsPageBindingModel> =
        MutableLiveData()

    // endregion
}