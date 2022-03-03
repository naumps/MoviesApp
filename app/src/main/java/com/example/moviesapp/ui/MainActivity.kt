package com.example.moviesapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ActivityMainBinding
import com.example.moviesapp.di.moviesModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * MainActivity used as launcher and container.
 */
class MainActivity : AppCompatActivity() {

  private lateinit var appBarConfiguration: AppBarConfiguration
  private val views: ActivityMainBinding by lazy {
    DataBindingUtil.setContentView(this, R.layout.activity_main)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initKoin()
    setupViews()
  }

  override fun onSupportNavigateUp(): Boolean {
    val navController = findNavController(R.id.nav_host_fragment_content_main)
    return navController.navigateUp(appBarConfiguration)
      || super.onSupportNavigateUp()
  }

  private fun initKoin() {
    startKoin {
      androidContext(this@MainActivity)
      modules(listOf(moviesModule))
    }
  }

  private fun setupViews() {
    views.apply {
      lifecycleOwner = this@MainActivity
    }
    setSupportActionBar(views.toolbar)
    val navController = findNavController(R.id.nav_host_fragment_content_main)
    appBarConfiguration = AppBarConfiguration(navController.graph)
    setupActionBarWithNavController(navController, appBarConfiguration)
  }
}