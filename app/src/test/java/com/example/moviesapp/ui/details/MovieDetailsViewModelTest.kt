package com.example.moviesapp.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.moviesapp.MOVIE_ID
import com.example.moviesapp.data.Resource
import com.example.moviesapp.genericError
import com.example.moviesapp.movieModel
import com.example.moviesapp.service.MoviesService
import com.example.moviesapp.utils.MainCoroutineRule
import com.example.moviesapp.utils.testing.getOrAwaitValue
import com.example.moviesapp.utils.testing.observeForTesting
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class MovieDetailsViewModelTest {
  @get:Rule
  var mainCoroutineRule = MainCoroutineRule()

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @Mock
  lateinit var service: MoviesService

  private lateinit var viewModel: MovieDetailsViewModel

  @Before
  fun setupViewModel() {
    MockitoAnnotations.openMocks(this)
    viewModel = MovieDetailsViewModel(service)
  }

  @Test
  fun testShould_GetMoviesSuccessful() = mainCoroutineRule.runBlockingTest {
    // Given
    Mockito.doReturn(Resource.success(movieModel)).`when`(service)
      .getMovieDetails(MOVIE_ID)
    // When
    viewModel.getMovieDetails(MOVIE_ID)
    // Then
    Mockito.verify(service).getMovieDetails(MOVIE_ID)
    Mockito.verifyNoMoreInteractions(service)

    viewModel.movie.observeForTesting {
      val movies = viewModel.movie.getOrAwaitValue()
      assertEquals(Resource.State.SUCCESS, movies.state)
      assertNull(movies.error)
      assertNotNull(movies.data)
      assertEquals(movieModel, movies.data)
    }
  }

  @Test
  fun testShould_FailToGetMovies_When_ApiReturnsError() =
    mainCoroutineRule.runBlockingTest {
      // Given
      Mockito.doReturn(Resource.error(genericError, null)).`when`(service)
        .getMovieDetails(MOVIE_ID)
      // When
      viewModel.getMovieDetails(MOVIE_ID)
      // Then
      Mockito.verify(service).getMovieDetails(MOVIE_ID)
      Mockito.verifyNoMoreInteractions(service)

      viewModel.movie.observeForTesting {
        val response = viewModel.movie.getOrAwaitValue()
        assertEquals(Resource.State.ERROR, response.state)
        assertEquals(genericError, response.error)
        org.junit.Assert.assertNull(response.data)
      }
    }
}