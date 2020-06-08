package personal.ivan.higgshomework.binding_model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import personal.ivan.higgshomework.BR
import personal.ivan.higgshomework.io.model.GitHubUserDetails
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

// region User Details Page

data class UserDetailsPageBindingModel(
    val avatarUrl: String,
    val username: String,
    val admin: Boolean,
    val biography: String,
    val location: String,
    val blogUrl: String
) {
    constructor(data: GitHubUserDetails) : this(
        avatarUrl = data.avatarUrl ?: "",
        username = data.username,
        admin = data.admin ?: false,
        biography = data.biography ?: "N/A",
        location = data.location ?: "N/A",
        blogUrl = data.blogUrl ?: "N/A"
    )
}

// endregion