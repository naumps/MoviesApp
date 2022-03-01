package com.example.moviesapp.network.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.parcelize.Parcelize

@Parcelize
data class PopularMoviesResponse(
  @JsonProperty("page")
  val page: Int,
  @JsonProperty("results")
  val results: ArrayList<MovieModel>,
): Parcelable
