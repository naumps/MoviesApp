package com.example.moviesapp

import com.example.moviesapp.data.Error
import com.example.moviesapp.network.model.MovieModel
import com.example.moviesapp.network.model.PopularMoviesResponse

internal val genericError = Error(0, "Test title", "Test message")

internal const val API_KEY = "0fc7527c43e353e4d4c479d9deb732c1"
internal const val MOVIE_ID = 12345
internal val movieModel = MovieModel(123,
  "Lord Of The Rings",
  "overview",
  "release date",
  10.0f,
  "path")

internal val movieModel2 = MovieModel(123,
  "The Godfather",
  "overview",
  "release date",
  10.0f,
  "path")

internal val movieResponse = PopularMoviesResponse(1, arrayListOf(movieModel, movieModel2))