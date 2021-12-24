package com.stefan.lab3.ui

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.stefan.lab3.MoviesRecyclerViewAdapter
import com.stefan.lab3.R
import com.stefan.lab3.api.MovieApi
import com.stefan.lab3.api.MovieApiClient
import com.stefan.lab3.databinding.FragmentFirstBinding
import com.stefan.lab3.models.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class FirstFragment : Fragment(), CellClickListener {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieApiClient: MovieApi
    private val movies: MutableList<Movie> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        binding.rvMoviesList.layoutManager = LinearLayoutManager(activity)
        binding.rvMoviesList.adapter = MoviesRecyclerViewAdapter(movies, this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieApiClient = MovieApiClient.getMovieApi()!!

        binding.etMovieTitle.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                val title: String = binding.etMovieTitle.text.toString()
                activity?.hideSoftKeyboard()
                searchMovieByTitle(title)
                true
            } else {
                Toast.makeText(activity, "Error!", Toast.LENGTH_LONG).show()
                false
            }
        }
    }

    private fun searchMovieByTitle(title: String) {
        movieApiClient.getMovieByTitle(title).enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if (response.code() == 200) {
                    try {
                        val responseMovie = response.body()
                        if (responseMovie != null) {
                            movies.add(
                                Movie(
                                    responseMovie.imdbID,
                                    responseMovie.Title,
                                    responseMovie.Year,
                                    responseMovie.Rated,
                                    responseMovie.Released,
                                    responseMovie.Runtime,
                                    responseMovie.Genre,
                                    responseMovie.Director,
                                    responseMovie.Actors,
                                    responseMovie.Plot,
                                    responseMovie.Poster,
                                    responseMovie.imdbRating,
                                    responseMovie.BoxOffice,
                                )
                            )
                        }
                        binding.rvMoviesList.adapter?.notifyItemInserted(movies.size - 1)
                        binding.etMovieTitle.text.clear()
                    } catch (e: Exception) {
                        Toast.makeText(
                            activity,
                            "Error finding movie!",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Toast.makeText(activity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCellClickListener(data: Movie) {
        val bundle = bundleOf("movieId" to data.imdbID)
        view?.findNavController()?.navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
    }
}

fun Activity.hideSoftKeyboard() {
    (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
        hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}

interface CellClickListener {
    fun onCellClickListener(data: Movie)
}