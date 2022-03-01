package com.example.moviesapp.data

/**
 * Data class that encapsulates a state, a successful outcome with a value of type [T]
 * an error value of type [Error] or exception.
 */
data class Resource<out T>(
  val state: State,
  val data: T?,
  val error: Error?,
  val exception: Exception? = null,
) {

  /**
   * Resource states.
   */
  enum class State {
    LOADING,
    SUCCESS,
    ERROR,
    EXCEPTION
  }

  companion object {

    /**
     * Returns an instance with state [State.LOADING] and an optional [data] value.
     *
     * @param data optional data value.
     * @return [Resource] with state [State.LOADING].
     */
    fun <T> loading(data: T? = null) = Resource(State.LOADING, data, null)

    /**
     * Returns an instance that encapsulates the given [data] as successful value.
     *
     * @param data the data that is being returned.
     * @return [Resource] with state [State.SUCCESS].
     */
    fun <T> success(data: T? = null) = Resource(State.SUCCESS, data, null)

    /**
     * Returns an instance that encapsulates the given [error] as failure value.
     *
     * @param error the error that is being returned.
     * @param data optional data value.
     * @return [Resource] with state [State.ERROR].
     */
    fun <T> error(error: Error, data: T? = null) = Resource(State.ERROR, data, error)

    /**
     * Returns an instance that represents the exception by type.
     *
     * @param data optional data value.
     * @return [Resource] with state [State.EXCEPTION].
     */
    fun <T> exception(exception: Exception, data: T? = null) =
      Resource(State.EXCEPTION, data, null, exception)
  }
}