package personal.ivan.higgshomework.ui_utils

import android.content.Context
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import personal.ivan.higgshomework.binding_model.UserSummaryVhBindingModel
import personal.ivan.higgshomework.databinding.VhUserSummaryBinding
import personal.ivan.higgshomework.io.model.GitHubUserSummary
import personal.ivan.higgshomework.view_model.MainViewModel

object UiUtil {

    // region Avoid Double Click

    // limit the click event in milli seconds
    private const val MILLI_SECONDS_RESTRICT = 500L

    // use for record last UI clicked unix time
    private var lastUiClickedUnixTime = 0L

    /**
     * Avoid UI double click
     */
    fun allowClick(): Boolean {
        // if the click event is too close to last unix time, than reject it
        if (SystemClock.elapsedRealtime() - lastUiClickedUnixTime < MILLI_SECONDS_RESTRICT) {
            return false
        }
        // save the click unix time
        lastUiClickedUnixTime = SystemClock.elapsedRealtime()
        return true
    }

    // endregion

    // region Recycler View

    /**
     * Inflate view data binding
     * note : use this function if the override method DOES NOT provide inflater or it may crash App
     *
     * @param context must be UI context
     */
    fun <T : ViewDataBinding> inflateViewBinding(
        context: Context,
        @LayoutRes layoutResId: Int,
        parent: ViewGroup? = null
    ): T =
        DataBindingUtil.inflate(
            LayoutInflater.from(context),
            layoutResId,
            parent,
            false
        ) as T

    /**
     * View holder class for GitHub user summary
     */
    class UserSummaryViewHolder(private val binding: VhUserSummaryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: UserSummaryVhBindingModel, viewModel: MainViewModel) {
            binding.apply {
                this.model = model
                this.viewModel = viewModel
                executePendingBindings()
            }
        }
    }

    //endregion
}