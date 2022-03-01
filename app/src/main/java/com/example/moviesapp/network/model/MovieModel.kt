package com.example.moviesapp.network.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieModel(
  @JsonProperty("id")
  val id: Int,
  @JsonProperty("original_title")
  val originalTitle: String,
  @JsonProperty("overview")
  val overview: String,
  @JsonProperty("release_date")
  val releaseDate: String,
  @JsonProperty("vote_average")
  val rating: Float,
  @JsonProperty("poster_path")
  val imagePath: String,
) : Parcelable
