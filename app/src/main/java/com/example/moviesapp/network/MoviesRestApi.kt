package com.example.moviesapp.network

import com.example.moviesapp.network.model.MovieModel
import com.example.moviesapp.network.model.PopularMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Rest interface for movies.
 */
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

  /**
   * Retrieves the movie details.
   */
  @GET("3/movie/{movie_id}")
  suspend fun getMovieDetails(
    @Path("movie_id")
    movieId: Int,
    @Query("api_key")
    apiKey: String,
  ):Response<MovieModel>
}