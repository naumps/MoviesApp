package com.example.moviesapp.network

import com.example.moviesapp.network.model.PopularMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Rest interface for movies.
 */
//3/movie/popular?api_key=0fc7527c43e353e4d4c479d9deb732c1&language=en-US&page=1

interface MoviesRestApi {
  /**
   * Retrieves the list of popular movies.
   */
  @GET("3/movie/popular")
  suspend fun getPopularMovies(
    @Query("api_key")
     apiKey: String,
    @Query("page")
    page:Int,
  ): Response<PopularMoviesResponse>
}