package com.example.moviesapp.service

import com.example.moviesapp.data.Resource
import com.example.moviesapp.network.model.PopularMoviesResponse

/**
 * Interface for the movies repository.
 */
interface MoviesService {

  /**
   * Retrieves the list of movies.
   * @param page [Int].
   */
  suspend fun getPopularMovies(page: Int): Resource<PopularMoviesResponse>
}