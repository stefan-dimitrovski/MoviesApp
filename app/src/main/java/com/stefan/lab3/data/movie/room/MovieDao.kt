package com.stefan.lab3.data.movie.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stefan.lab3.domain.movie.model.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

    @Query("SELECT * FROM movie")
    fun getAllMovies(): List<Movie>

    @Query("SELECT * FROM movie WHERE imdbID IN (:movieId) LIMIT 1")
    fun findById(movieId: String): Movie

    @Query("DELETE FROM movie")
    fun deleteAll()
}