package com.stefan.lab3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stefan.lab3.models.Movie
import com.stefan.lab3.ui.CellClickListener

class MoviesRecyclerViewAdapter(
    private val movies: MutableList<Movie>,
    private val cellClickListener: CellClickListener
) : RecyclerView.Adapter<MoviesRecyclerViewAdapter.ViewHolder>() {

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