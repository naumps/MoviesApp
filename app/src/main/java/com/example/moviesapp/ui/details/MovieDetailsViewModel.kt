package com.example.moviesapp.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.data.Resource
import com.example.moviesapp.network.model.MovieModel
import com.example.moviesapp.service.MoviesService
import com.example.moviesapp.ui.overview.MoviesOverviewFragment
import kotlinx.coroutines.launch

/**
 * ViewModel used in [MoviesOverviewFragment].
 */
class MovieDetailsViewModel(private val service: MoviesService) : ViewModel() {

  private val _movie = MutableLiveData<Resource<MovieModel>>()
  val movie: LiveData<Resource<MovieModel>> = _movie

  /**
   * Retrieves more details for the movie with movieId [Int].
   *
   * @param movieId [Int]
   */
  fun getMovieDetails(movieId: Int) = viewModelScope.launch {
    _movie.postValue(Resource.loading())
    _movie.postValue(service.getMovieDetails(movieId))
  }
}