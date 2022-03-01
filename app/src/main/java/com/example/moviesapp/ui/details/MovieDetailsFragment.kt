package com.example.moviesapp.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviesapp.databinding.FragmentMovieDetailsBinding
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Fragment used for displaying movie details.
 */
class MovieDetailsFragment : Fragment() {

  private var _binding: FragmentMovieDetailsBinding? = null

  private val views get() = _binding!!
  private val viewModel: MovieDetailsViewModel by viewModel()


  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {

    _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
    return views.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val id = arguments?.getInt("movieId")
    viewModel.getMovieDetails(id?:0)
    viewModel.movie.observe(viewLifecycleOwner) {

    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}