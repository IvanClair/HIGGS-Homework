package personal.ivan.higgshomework.ui_utils

import android.app.Activity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import personal.ivan.higgshomework.R

/**
 * Show IO alert
 */
fun Activity.showIoAlert() {
    MaterialAlertDialogBuilder(this)
        .setTitle(R.string.alert_title)
        .setMessage(R.string.alert_message)
        .setPositiveButton(R.string.label_ok, null)
        .show()
}