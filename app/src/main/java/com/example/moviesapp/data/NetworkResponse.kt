package com.example.moviesapp.data

/**
 * Class that represents a response coming from a network call.
 */
sealed class NetworkResponse<out T : Any> {

  /**
   * Data class that represents a successful response.
   */
  data class Success<T : Any>(val body: T?) : NetworkResponse<T>()

  /**
   * Data class that represents an error received from the backend.
   */
  data class ApiError(
    val code: Int,
    val title: String? = null,
    val message: String? = null,
    val extra: Map<String, String?>? = null,
  ) : NetworkResponse<Nothing>()

  /**
   * Data class that represents the network exception.
   */
  data class NetworkException(val exception: Exception) : NetworkResponse<Nothing>()
}

/**
 * Maps a [NetworkResponse.ApiError] to [Error].
 */
fun NetworkResponse.ApiError.toError() = Error(code, title, message, extra)

/**
 * Maps a [NetworkResponse] to a [Resource].
 *
 * @param onSuccess optional method for handling the successful response.
 */
fun <T : Any, R : Any> NetworkResponse<T>.toResource(
  onSuccess: (() -> Resource<R>)? = null,
) = handleResponseStatus(null, onSuccess)

private fun <T : Any, R : Any> NetworkResponse<T>.handleResponseStatus(
  data: R? = null,
  onSuccess: (() -> Resource<R>)?,
): Resource<R> = when (this) {
  is NetworkResponse.Success -> onSuccess?.invoke() ?: Resource.success(body as R?)
  is NetworkResponse.ApiError -> Resource.error(toError(), data)
  is NetworkResponse.NetworkException -> Resource.exception(exception, data)
}