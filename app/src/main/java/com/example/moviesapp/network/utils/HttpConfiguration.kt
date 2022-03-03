package com.example.moviesapp.network.utils

import com.example.moviesapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * Handles the HTTP configuration.
 */
object HttpConfiguration {

  /**
   * Creates HTTP client Builder.
   *
   * @return [OkHttpClient] The HTTP client Builder.
   */
  internal fun getHttpBuilder(): OkHttpClient.Builder {
    val httpClient = OkHttpClient.Builder()
    val timeout = 5L
    httpClient
      .readTimeout(timeout, TimeUnit.SECONDS)
      .connectTimeout(timeout, TimeUnit.SECONDS)

    if (BuildConfig.DEBUG) {
      val loggingInterceptor = HttpLoggingInterceptor()
      loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
      httpClient.addInterceptor(loggingInterceptor)
    }
    return httpClient
  }
}