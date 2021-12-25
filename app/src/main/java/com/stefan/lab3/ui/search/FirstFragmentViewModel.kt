package com.stefan.lab3.ui.search

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.stefan.lab3.data.movie.retrofit.MovieApi
import com.stefan.lab3.data.movie.retrofit.MovieApiProvider
import com.stefan.lab3.data.movie.room.AppDatabase
import com.stefan.lab3.domain.movie.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class FirstFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private var movieApiClient: MovieApi = MovieApiProvider.getMovieApi()!!
    private var app: Application = application
    private var database: AppDatabase = AppDatabase.getInstance(application)
    private var moviesMutableLiveData: MutableLiveData<Movie> = MutableLiveData<Movie>()

    fun searchMovieByTitle(title: String) {
        movieApiClient.getMovieByTitle(title).enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if (response.code() == 200) {
                    try {
                        val currentMovie = response.body()
                        saveMovieInDatabase(currentMovie!!)
                        moviesMutableLiveData.value = currentMovie
                    } catch (e: Exception) {
                        Toast.makeText(
                            app,
                            "Error finding movie!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        app,
                        "Error!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Toast.makeText(app, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun saveMovieInDatabase(currentMovie: Movie) {
        CoroutineScope(Dispatchers.IO).launch {
            database.movieDao().insertMovie(currentMovie)
        }
    }

    fun getMovieMutableLiveData(): MutableLiveData<Movie> {
        return moviesMutableLiveData
    }

}