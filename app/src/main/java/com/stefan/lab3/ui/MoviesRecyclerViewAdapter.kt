package com.stefan.lab3.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stefan.lab3.R
import com.stefan.lab3.data.movie.room.MovieDao
import com.stefan.lab3.domain.movie.model.Movie
import com.stefan.lab3.ui.search.CellClickListener

class MoviesRecyclerViewAdapter(
    private val cellClickListener: CellClickListener
) : RecyclerView.Adapter<MoviesRecyclerViewAdapter.ViewHolder>() {

    private val movies: MutableList<Movie> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun updateMovies(movies: List<Movie>?) {
        this.movies.clear()
        if (movies != null) {
            this.movies.addAll(movies)
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tv_MovieTitle)
        val year: TextView = view.findViewById(R.id.tv_MovieYear)
        val poster: ImageView = view.findViewById(R.id.iv_MoviePoster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val movieView = inflater.inflate(R.layout.fragment_movie_list_row, parent, false)

        return ViewHolder(movieView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movieItem: Movie = movies[position]

        holder.title.text = movieItem.Title
        holder.year.text = movieItem.Year
        Glide.with(holder.itemView).load(movieItem.Poster).into(holder.poster)

        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(movieItem)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

}