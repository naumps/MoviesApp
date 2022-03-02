package com.example.moviesapp.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.moviesapp.R
import com.example.moviesapp.data.Resource
import com.example.moviesapp.databinding.FragmentMovieDetailsBinding
import com.example.moviesapp.network.utils.RestClient
import com.example.moviesapp.ui.overview.MoviesOverviewFragment.Companion.MOVIE_ID_KEY
import com.example.moviesapp.utils.loadImage
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Fragment used for displaying movie details.
 */
class MovieDetailsFragment : Fragment() {

  private var _binding: FragmentMovieDetailsBinding? = null
  private val viewModel: MovieDetailsViewModel by viewModel()
  private val views get() = _binding!!


  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {
    _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
    return views.root
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val id = arguments?.getInt(MOVIE_ID_KEY)
    id?.let {
      viewModel.getMovieDetails(id)
    }
    initObserver()
  }

  private fun initObserver() {
    viewModel.movie.observe(viewLifecycleOwner) {
      when (it.state) {
        Resource.State.LOADING -> {
          views.progressBar.visibility = View.VISIBLE
        }
        Resource.State.SUCCESS -> {
          it.data?.let { movie ->
            views.movie =
              movie.copy(releaseDate = getString(R.string.release_date, movie.releaseDate))
            views.imageView.loadImage(RestClient.IMAGE_URL + movie.imagePath, 0, false)
          }
          views.progressBar.visibility = View.GONE
        }
        Resource.State.ERROR, Resource.State.EXCEPTION -> {
          views.progressBar.visibility = View.GONE
          Toast.makeText(requireContext(),
            "Something went wrong, please try again :(",
            Toast.LENGTH_SHORT).show()
          //TODO: Create a generic error/exception handler.
        }
      }
    }
  }
}