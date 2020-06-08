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
import personal.ivan.higgshomework.io.model.IoStatus
import personal.ivan.higgshomework.repository.GitHubRepository
import personal.ivan.higgshomework.ui_utils.UiUtil
import personal.ivan.higgshomework.view.user_list.UserListFragmentDirections
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: GitHubRepository) : ViewModel() {

    // IO Status
    val ioStatus: MutableLiveData<IoStatus> = MutableLiveData<IoStatus>()

    // Clear Keyboard Focus
    val clearFocusTrigger = MutableLiveData<Boolean>()

    // region User List Page

    // page binding model
    val userListPageBindingModel: LiveData<UserListPageBindingModel> =
        MutableLiveData<UserListPageBindingModel>().apply {
            value = UserListPageBindingModel()
        }

    // user list data list from IO
    val getUserPagedList: LiveData<PagedList<UserSummaryVhBindingModel>> =
        repository.getUserPagedList(scope = viewModelScope, ioStatus = ioStatus)

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
     * Search user clicked
     */
    fun searchUserOnClick(view: View) {
        if (UiUtil.allowClick()) {
            // navigate to search users page
            view.findNavController().navigate(UserListFragmentDirections.navigateToSearchUsers())
        }
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
            // navigate to user details page
            view.findNavController().navigate(
                UserListFragmentDirections.navigateToUserDetails(username = model.username),
                FragmentNavigatorExtras(avatar to model.username)
            )
        }
    }

    // region Search Users

    fun startSearchUsers(query: String) {

    }

    // endregion

    // endregion

    // region User Details Page

    /**
     * Get user details live data
     */
    fun getUserDetails(username: String): LiveData<UserDetailsPageBindingModel> =
        repository.getUserDetails(username = username, ioStatus = ioStatus)

    // endregion
}