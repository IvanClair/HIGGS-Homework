package personal.ivan.higgshomework.view.user_list

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import personal.ivan.higgshomework.R
import personal.ivan.higgshomework.io.model.GitHubUserSummary
import personal.ivan.higgshomework.ui_utils.UiUtil
import personal.ivan.higgshomework.view_model.MainViewModel
import javax.inject.Inject

class UserListAdapter @Inject constructor(private val viewModel: MainViewModel) :
    PagedListAdapter<GitHubUserSummary, UiUtil.UserSummaryViewHolder>(DIFF_CALLBACK) {

    // region Override

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UiUtil.UserSummaryViewHolder =
        UiUtil.UserSummaryViewHolder(
            UiUtil.inflateViewBinding(
                context = parent.context,
                layoutResId = R.layout.vh_user_summary,
                parent = parent
            )
        )

    override fun onBindViewHolder(holder: UiUtil.UserSummaryViewHolder, position: Int) {
        getItem(position)?.also {
            holder.bind(model = it, viewModel = viewModel)
        }
    }

    // endregion

    // region Difference Callback

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GitHubUserSummary>() {
            override fun areItemsTheSame(
                oldItem: GitHubUserSummary,
                newItem: GitHubUserSummary
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: GitHubUserSummary,
                newItem: GitHubUserSummary
            ): Boolean = oldItem.hashCode() == newItem.hashCode()
        }
    }

    // endregion
}