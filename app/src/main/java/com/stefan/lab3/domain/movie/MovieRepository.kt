package com.stefan.lab3.domain.movie

import com.stefan.lab3.data.movie.retrofit.MovieApi
import com.stefan.lab3.data.movie.room.MovieDao
import com.stefan.lab3.domain.movie.model.Movie

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

    fun getMovieById(movieId: String): Movie {
        return movieDao.findById(movieId)
    }

    fun getAllMovies(): List<Movie> {
        return movieDao.getAllMovies()
    }

    fun deleteAllMovies() {
        movieDao.deleteAll()
    }

    private fun saveInDatabase(currentMovie: Movie) {
        movieDao.insertMovie(currentMovie)
    }

}