package com.example.moviesapp.ui.overview

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.moviesapp.data.Resource
import com.example.moviesapp.genericError
import com.example.moviesapp.movieModel
import com.example.moviesapp.movieModel2
import com.example.moviesapp.movieResponse
import com.example.moviesapp.service.MoviesService
import com.example.moviesapp.utils.MainCoroutineRule
import com.example.moviesapp.utils.testing.getOrAwaitValue
import com.example.moviesapp.utils.testing.observeForTesting
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
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
class MoviesOverviewViewModelTest {

  @get:Rule
  var mainCoroutineRule = MainCoroutineRule()

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @Mock
  lateinit var service: MoviesService

  private lateinit var viewModel: MoviesOverviewViewModel

  @Before
  fun setupViewModel() {
    MockitoAnnotations.openMocks(this)
    viewModel = MoviesOverviewViewModel(service)
  }

  @Test
  fun testShould_GetMoviesSuccessful() = mainCoroutineRule.runBlockingTest {
    // Given
    Mockito.doReturn(Resource.success(movieResponse)).`when`(service)
      .getPopularMovies(1)
    // When
    viewModel.getMovies()
    // Then
    Mockito.verify(service).getPopularMovies(1)
    Mockito.verifyNoMoreInteractions(service)

    viewModel.movies.observeForTesting {
      val movies = viewModel.movies.getOrAwaitValue()
      assertEquals(Resource.State.SUCCESS, movies.state)
      Assert.assertNull(movies.error)
      Assert.assertNotNull(movies.data)
      Assert.assertEquals(2, movies.data?.results?.size)
      assertEquals(movieModel, movies.data?.results?.first())
      assertEquals(movieModel2, movies.data?.results?.get(1))
    }
  }

  @Test
  fun testShould_FailToGetMovies_When_ApiReturnsError() =
    mainCoroutineRule.runBlockingTest {
      // Given
      Mockito.doReturn(Resource.error(genericError, null)).`when`(service)
        .getPopularMovies(1)
      // When
      viewModel.getMovies()
      // Then
      Mockito.verify(service).getPopularMovies(1)
      Mockito.verifyNoMoreInteractions(service)

      viewModel.movies.observeForTesting {
        val response = viewModel.movies.getOrAwaitValue()
        assertEquals(Resource.State.ERROR, response.state)
        assertEquals(genericError, response.error)
        Assert.assertNull(response.data)
      }
    }
}