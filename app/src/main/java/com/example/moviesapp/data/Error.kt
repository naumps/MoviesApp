package com.example.moviesapp.data

import android.os.Parcelable
import com.example.moviesapp.network.utils.ErrorCode
import kotlinx.parcelize.Parcelize

/**
 * Data class that contains information about an error or exception.
 */
@Parcelize
data class Error(
  val code: Int? = ErrorCode.UNKNOWN,
  val title: String? = null,
  val message: String? = null,
  val extra: Map<String, String?>? = null,
) : Parcelable