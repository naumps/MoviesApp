package com.example.moviesapp.utils

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide

/**
 * Loads an image into the image view using the image loading library "Glide".
 *
 * @param path [String] of the image to be loaded.
 * @param placeHolder [Int] drawable resource used as fallback for failed image loading.
 * @param circleCrop [Boolean] flag indicating if the image should be circle cropped or not.
 */
fun ImageView.loadImage(
  path: String?,
  @DrawableRes placeHolder: Int,
  circleCrop: Boolean = false,
) {
  Glide.with(context)
    .load(path)
    .apply {
      if (circleCrop) {
        circleCrop()
      }
    }
    .error(placeHolder)
    .into(this)
}