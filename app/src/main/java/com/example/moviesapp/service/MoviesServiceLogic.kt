package com.example.moviesapp.service

import com.example.moviesapp.data.Resource
import com.example.moviesapp.data.toResource
import com.example.moviesapp.network.MoviesRestApi
import com.example.moviesapp.network.model.PopularMoviesResponse
import com.example.moviesapp.network.utils.managedApiCall

class MoviesServiceLogic(private val api: MoviesRestApi): MoviesService {

  override suspend fun getPopularMovies(page:Int): Resource<PopularMoviesResponse> {
    val response = managedApiCall {
      api.getPopularMovies("0fc7527c43e353e4d4c479d9deb732c1",page)
    }
    return response.toResource()
  }
}