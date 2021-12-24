package com.stefan.lab3.database.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.stefan.lab3.models.Movie

interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)
}