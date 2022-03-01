package com.example.moviesapp.ui.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.data.Resource
import com.example.moviesapp.network.model.PopularMoviesResponse
import com.example.moviesapp.service.MoviesService
import kotlinx.coroutines.launch

/**
 * ViewModel used in [MoviesOverviewFragment].
 */
class MoviesOverviewViewModel(private val service: MoviesService) : ViewModel() {

  private val _movies = MutableLiveData<Resource<PopularMoviesResponse>>()
  val movies: LiveData<Resource<PopularMoviesResponse>> = _movies
  private var page = 1

  /**
   * Retrieves the list of popular movies.
   */
  fun getMovies() = viewModelScope.launch {
    _movies.value = Resource.loading()
    _movies.postValue(service.getPopularMovies(page++))
  }
}