package personal.ivan.higgshomework.view_model

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.*
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
     * User clicked an user
     */
    fun userOnClickListener(
        view: View,
        model: UserSummaryVhBindingModel,
        avatar: ImageView
    ) {
        if (UiUtil.allowClick()) {
            // hit API
            selectedUsername.value = model.username

            // navigate to user details page
            view.findNavController().navigate(
                UserListFragmentDirections.navigateToUserDetails(username = model.username),
                FragmentNavigatorExtras(avatar to model.username)
            )
        }
    }

    // endregion

    // region User Details Page

    // selected username, use for trigger API
    private val selectedUsername: MutableLiveData<String> = MutableLiveData()

    // user details page binding model
    val userDetailsPageBindingModel: LiveData<UserDetailsPageBindingModel> =
        selectedUsername.switchMap {
            repository.getUserDetails(
                username = it, ioStatus = ioStatus
            )
        }

    // endregion
}