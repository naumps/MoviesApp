package com.example.moviesapp.di

import com.example.moviesapp.network.MoviesRestApi
import com.example.moviesapp.network.utils.RestClient
import com.example.moviesapp.network.utils.HttpConfiguration
import com.example.moviesapp.service.MoviesService
import com.example.moviesapp.service.MoviesServiceLogic
import com.example.moviesapp.ui.details.MovieDetailsViewModel
import com.example.moviesapp.ui.overview.MoviesOverviewViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moviesModule = module {
  single { HttpConfiguration.getHttpBuilder().build() }
  single { RestClient.createService(MoviesRestApi::class.java) }
  single<MoviesService> { MoviesServiceLogic(get()) }
  viewModel { MoviesOverviewViewModel(get()) }
  viewModel { MovieDetailsViewModel(get()) }
}