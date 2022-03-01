package com.example.moviesapp.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.data.Resource
import com.example.moviesapp.databinding.FragmentMoviesOverviewBinding
import com.example.moviesapp.ui.overview.adapter.MoviesOverviewAdapter
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MoviesOverviewFragment : Fragment() {

  private var _binding: FragmentMoviesOverviewBinding? = null
  private val viewModel: MoviesOverviewViewModel by viewModel()
  private val moviesAdapter = MoviesOverviewAdapter()

  private val views get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {

    _binding = FragmentMoviesOverviewBinding.inflate(inflater, container, false)
    return views.root
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupViews()
    setupObserver()
    initListeners()
    viewModel.getMovies()
  }

  private fun setupViews() {
    views.moviesList.apply {
      this.adapter = moviesAdapter
      this.layoutManager = LinearLayoutManager(requireContext())
    }
  }

  private fun setupObserver() {
    viewModel.movies.observe(viewLifecycleOwner) {
      when (it.state) {
        Resource.State.LOADING -> {
          views.progressBar.visibility = View.VISIBLE
        }
        Resource.State.SUCCESS -> {
          it.data?.results?.let { movies -> moviesAdapter.setItems(movies) }
          views.progressBar.visibility = View.GONE
        }
      }
    }
  }

  private fun initListeners() {
    views.moviesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (!recyclerView.canScrollVertically(1) && dy > 0) {
          viewModel.getMovies()
        }
      }
    })
  }
}
