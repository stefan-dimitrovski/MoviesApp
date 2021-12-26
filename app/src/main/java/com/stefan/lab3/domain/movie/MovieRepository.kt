package com.stefan.lab3.domain.movie

import com.stefan.lab3.data.movie.retrofit.MovieApi
import com.stefan.lab3.data.movie.room.MovieDao
import com.stefan.lab3.domain.movie.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MovieRepository(
    private val movieDao: MovieDao,
    private val movieApi: MovieApi
) {

    fun searchMovieByTitle(title: String) {
        movieApi.getMovieByTitle(title).enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if (response.code() == 200) {
                    try {
                        val currentMovie = response.body()
                        if (!currentMovie?.imdbID.isNullOrBlank()) {
                            saveInDatabase(currentMovie!!)
                        }
                    } catch (e: Exception) {
                        print(e)
                    }
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {

            }
        })
    }

    private fun saveInDatabase(currentMovie: Movie) {
        CoroutineScope(Dispatchers.IO).launch {
            movieDao.insertMovie(currentMovie)
        }
    }

    fun getAllMovies(): List<Movie> {
        return movieDao.getAllMovies()
    }

}