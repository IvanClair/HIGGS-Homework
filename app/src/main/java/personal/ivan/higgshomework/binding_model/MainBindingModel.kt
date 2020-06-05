package personal.ivan.higgshomework.binding_model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import personal.ivan.higgshomework.BR

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

// endregion