package com.example.moviesapp.service

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.moviesapp.API_KEY
import com.example.moviesapp.MOVIE_ID
import com.example.moviesapp.data.Resource
import com.example.moviesapp.movieModel
import com.example.moviesapp.movieResponse
import com.example.moviesapp.network.MoviesRestApi
import com.example.moviesapp.utils.MainCoroutineRule
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
class MoviesServiceLogicTest {

  @get:Rule
  val mainCoroutineRule = MainCoroutineRule()

  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  @Mock
  private lateinit var api: MoviesRestApi

  private lateinit var service: MoviesService

  @Before
  fun setUp() {
    MockitoAnnotations.openMocks(this)
    service = MoviesServiceLogic(api)
  }

  @Test
  fun testShould_getPopularMovies_successfully() = runBlocking {
    //Given
    Mockito.`when`(
      api.getPopularMovies(API_KEY, 1)
    ).thenReturn(
      Response.success(
        movieResponse
      )
    )
    //When
    val result = service.getPopularMovies(1)
    //Then
    Mockito.verify(api).getPopularMovies(API_KEY, 1)
    Mockito.verifyNoMoreInteractions(api)
    assertEquals(Resource.State.SUCCESS, result.state)
    Assert.assertNotNull(result.data)
  }


  @Test
  fun testShould_FailToGetMovies_When_ApiReturnsError() = runBlocking {
    //Given
    Mockito.`when`(
      api.getPopularMovies(API_KEY, 1)
    ).thenReturn(Response.error(400, "".toResponseBody()))
    //When
    val result = service.getPopularMovies(1)
    //Then
    Mockito.verify(api).getPopularMovies(API_KEY, 1)

    Mockito.verifyNoMoreInteractions(api)
    assertEquals(Resource.State.ERROR, result.state)
    Assert.assertNull(result.data)
  }

  @Test
  fun testShould_ThrowException_When_GetMovies() = runBlocking {
    //Given
    Mockito.`when`(
      api.getPopularMovies(API_KEY, 1)
    ).thenThrow(RuntimeException())
    //When
    val result = service.getPopularMovies(1)
    //Then
    Mockito.verify(api).getPopularMovies(API_KEY, 1)

    Mockito.verifyNoMoreInteractions(api)
    assertEquals(Resource.State.EXCEPTION, result.state)
    Assert.assertNull(result.data)
  }

  @Test
  fun testShould_getMovieID_successfully() = runBlocking {
    //Given
    Mockito.`when`(
      api.getMovieDetails(MOVIE_ID, API_KEY)
    ).thenReturn(
      Response.success(movieModel)
    )

    //When
    val result = service.getMovieDetails(MOVIE_ID)
    //Then
    Mockito.verify(api).getMovieDetails(MOVIE_ID, API_KEY)
    Mockito.verifyNoMoreInteractions(api)
    assertEquals(Resource.State.SUCCESS, result.state)
    Assert.assertNotNull(result.data)
  }

  @Test
  fun testShould_FailToGetMovieDetails_When_ApiReturnsError() = runBlocking {
    //Given
    Mockito.`when`(
      api.getMovieDetails(MOVIE_ID, API_KEY)
    ).thenReturn(Response.error(400, "".toResponseBody()))
    //When
    val result = service.getMovieDetails(MOVIE_ID)
    //Then
    Mockito.verify(api).getMovieDetails(MOVIE_ID, API_KEY)

    Mockito.verifyNoMoreInteractions(api)
    assertEquals(Resource.State.ERROR, result.state)
    Assert.assertNull(result.data)
  }

  @Test
  fun testShould_ThrowException_When_GetMovieDetails() = runBlocking {
    //Given
    Mockito.`when`(
      api.getMovieDetails(MOVIE_ID, API_KEY)
    ).thenThrow(RuntimeException())
    //When
    val result = service.getMovieDetails(MOVIE_ID)
    //Then
    Mockito.verify(api).getMovieDetails(MOVIE_ID, API_KEY)

    Mockito.verifyNoMoreInteractions(api)
    assertEquals(Resource.State.EXCEPTION, result.state)
    Assert.assertNull(result.data)
  }
}