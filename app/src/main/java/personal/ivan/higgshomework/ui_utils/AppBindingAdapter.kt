package personal.ivan.higgshomework.ui_utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import personal.ivan.higgshomework.di.GlideApp

/**
 * Loading web image by Glide
 */
@BindingAdapter("loadImage")
fun setLoadImage(imageView: ImageView, url: String?) {
    url?.also {
        GlideApp
            .with(imageView)
            .load(url)
            .into(imageView)
    }
}