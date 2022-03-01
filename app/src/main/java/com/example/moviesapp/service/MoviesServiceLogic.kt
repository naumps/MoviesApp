package com.example.moviesapp.service

import com.example.moviesapp.BuildConfig
import com.example.moviesapp.data.Resource
import com.example.moviesapp.data.toResource
import com.example.moviesapp.network.MoviesRestApi
import com.example.moviesapp.network.model.PopularMoviesResponse
import com.example.moviesapp.network.utils.managedApiCall

class MoviesServiceLogic(private val api: MoviesRestApi): MoviesService {

  override suspend fun getPopularMovies(page:Int): Resource<PopularMoviesResponse> {
    val response = managedApiCall {
      api.getPopularMovies(BuildConfig.MOVIE_DB_API_KEY,page)
    }
    return response.toResource()
  }
}