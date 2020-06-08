package personal.ivan.higgshomework.view

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import personal.ivan.higgshomework.R
import personal.ivan.higgshomework.binding_model.UserSummaryVhBindingModel
import personal.ivan.higgshomework.ui_utils.UiUtil
import personal.ivan.higgshomework.view_model.MainViewModel
import javax.inject.Inject

class UserSummaryAdapter @Inject constructor(private val viewModel: MainViewModel) :
    PagedListAdapter<UserSummaryVhBindingModel, UiUtil.UserSummaryViewHolder>(DIFF_CALLBACK) {

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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserSummaryVhBindingModel>() {
            override fun areItemsTheSame(
                oldItem: UserSummaryVhBindingModel,
                newItem: UserSummaryVhBindingModel
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: UserSummaryVhBindingModel,
                newItem: UserSummaryVhBindingModel
            ): Boolean = oldItem.hashCode() == newItem.hashCode()
        }
    }

    // endregion
}