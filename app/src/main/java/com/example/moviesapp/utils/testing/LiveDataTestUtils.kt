package com.example.moviesapp.utils.testing

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Observes a [LiveData] until the `block` is done executing.
 */
fun <T> LiveData<T>.observeForTesting(block: () -> Unit) {
  val observer = Observer<T> { }
  try {
    observeForever(observer)
    block()
  } finally {
    removeObserver(observer)
  }
}

/**
 * Gets the value of a [LiveData] or waits for it to have one, with a timeout.
 */
@Suppress("UNCHECKED_CAST")
@VisibleForTesting(otherwise = VisibleForTesting.NONE)
fun <T> LiveData<T>.getOrAwaitValue(
  time: Long = 500,
  timeUnit: TimeUnit = TimeUnit.MILLISECONDS,
  afterObserve: () -> Unit = {}
): T {
  var data: T? = null
  val latch = CountDownLatch(1)
  val observer = object : Observer<T> {
    override fun onChanged(o: T?) {
      data = o
      latch.countDown()
      this@getOrAwaitValue.removeObserver(this)
    }
  }
  this.observeForever(observer)

  try {
    afterObserve.invoke()

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
      this.removeObserver(observer)
      throw TimeoutException("LiveData value was never set.")
    }
  } finally {
    this.removeObserver(observer)
  }

  return data as T
}