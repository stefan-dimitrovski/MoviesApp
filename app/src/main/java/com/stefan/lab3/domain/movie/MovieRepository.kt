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

    fun searchMovieByTitle(title: String): List<Movie> {
        val movieResponse = movieApi.getMovieByTitle(title)
        val currentMovie = movieResponse.execute().body()
        if (!currentMovie?.imdbID.isNullOrBlank()) {
            saveInDatabase(currentMovie!!)
        }
        return movieDao.getAllMovies()
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