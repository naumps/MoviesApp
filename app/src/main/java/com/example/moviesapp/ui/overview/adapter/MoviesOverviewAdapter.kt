package com.example.moviesapp.ui.overview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.databinding.MovieListItemBinding
import com.example.moviesapp.network.RestClient.IMAGE_URL
import com.example.moviesapp.network.model.MovieModel
import com.example.moviesapp.utils.loadImage

/**
 * [RecyclerView.Adapter] used for displaying movie items.
 */
class MoviesOverviewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  private val movies = ArrayList<MovieModel>()

  fun setItems(newItems: List<MovieModel>) {
    movies.addAll(newItems)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    val views = MovieListItemBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false)
    return MovieViewHolder(views)
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    val movie = movies[position]
    (holder as MovieViewHolder).bind(movie)
  }

  override fun getItemCount() = movies.size

  /**
   * Represents a [RecyclerView.ViewHolder] for the movie items.
   */
  inner class MovieViewHolder(private val views: MovieListItemBinding) :
    RecyclerView.ViewHolder(views.root) {

    /**
     * ViewHolder binder.
     */
    fun bind(movieModel: MovieModel) {
      views.movie = movieModel
      views.icon.loadImage(IMAGE_URL + movieModel.imagePath, 0, true)
    }
  }
}