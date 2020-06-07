package personal.ivan.higgshomework.io.model

import androidx.annotation.StringDef

data class IoStatus(@Status val status: String) {
    companion object {

        // Status
        @StringDef(
            SUCCESS,
            FAIL,
            LOADING
        )
        @Retention(AnnotationRetention.SOURCE)
        annotation class Status

        const val SUCCESS = "SUCCESS"
        const val FAIL = "FAIL"
        const val LOADING = "LOADING"

        /**
         * Create success status
         */
        fun success(): IoStatus = IoStatus(status = SUCCESS)

        /**
         * Create fail status
         */
        fun fail(): IoStatus = IoStatus(status = FAIL)

        /**
         * Create loading status
         */
        fun loading(): IoStatus = IoStatus(status = LOADING)
    }
}