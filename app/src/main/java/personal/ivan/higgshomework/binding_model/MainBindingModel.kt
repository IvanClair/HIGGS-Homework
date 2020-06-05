package personal.ivan.higgshomework.binding_model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import personal.ivan.higgshomework.BR
import personal.ivan.higgshomework.io.model.GitHubUserSummary

// region User List Page

class UserListPageBindingModel : BaseObservable() {

    var showLoading: Boolean = true
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.showLoading)
        }

    var showSearchButton: Boolean = true
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.showSearchButton)
        }

    /**
     * Show or hide loading progress and search button
     */
    fun updateVisibility(
        enableLoading: Boolean,
        enableSearch: Boolean
    ) {
        showLoading = enableLoading
        showSearchButton = enableSearch
    }
}

/**
 * Data class for display user summary on view holder
 */
data class UserSummaryVhBindingModel(
    val avatarUrl: String,
    val username: String,
    val admin: Boolean
) {
    constructor(data: GitHubUserSummary) : this(
        avatarUrl = data.avatarUrl ?: "",
        username = data.username ?: "",
        admin = data.admin ?: false
    )
}

// endregion