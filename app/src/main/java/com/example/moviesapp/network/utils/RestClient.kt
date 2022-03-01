package com.example.moviesapp.network.utils

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import org.koin.java.KoinJavaComponent
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object RestClient {
  internal const val BASE_URL = "https://api.themoviedb.org/"
  internal const val IMAGE_URL = "https://image.tmdb.org/t/p/w200"

  private val httpClient: OkHttpClient by KoinJavaComponent.inject(OkHttpClient::class.java)

  private val retrofit: Retrofit by lazy {
    Retrofit.Builder()
      .baseUrl(BASE_URL)
      .client(httpClient)
      .addConverterFactory(createConverter())
      .build()
  }

  /**
   * Create an implementation of the API endpoints defined by the [serviceClass] interface.
   *
   * @param serviceClass the REST interface.
   */
  fun <T> createService(serviceClass: Class<T>): T{
    return retrofit.create(serviceClass)
  }

  private fun createConverter(): JacksonConverterFactory {
    val objectMapper = ObjectMapper()
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    return JacksonConverterFactory.create(objectMapper)
  }
}