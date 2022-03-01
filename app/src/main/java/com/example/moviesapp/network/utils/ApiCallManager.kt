package com.example.moviesapp.network.utils

import com.example.moviesapp.data.NetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * Function that executes the given [apiCall] with the provided [dispatcher] and wraps the result
 * in a [NetworkResponse].
 *
 * @param dispatcher the coroutine dispatcher or [Dispatchers.IO] as default.
 * @param apiCall suspend function that returns a [Response].
 * @return the success or error result wrapped in a [NetworkResponse].
 */
suspend fun <T : Any> managedApiCall(
  dispatcher: CoroutineDispatcher = Dispatchers.IO,
  apiCall: suspend () -> Response<T>,
): NetworkResponse<T> {
  return withContext(dispatcher) {
    try {
      val response = apiCall()
      if (response.isSuccessful) {
        handleSuccessfulResponse(response)
      } else {
        handleFailureResponse(response)
      }
    } catch (e: Exception) {
      NetworkResponse.NetworkException(e)
    }
  }
}

private fun <T : Any> handleSuccessfulResponse(response: Response<T>): NetworkResponse<T> {
  return NetworkResponse.Success(response.body())
}

private fun <T : Any> handleFailureResponse(response: Response<T>): NetworkResponse<T> =
  NetworkResponse.ApiError(response.code(), null, response.message())
