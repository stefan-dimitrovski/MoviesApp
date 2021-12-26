package com.stefan.lab3.domain.movie.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    @PrimaryKey
    var imdbID: String = "",
    var Title: String = "",
    var Year: String = "",
    var Rated: String = "",
    var Released: String = "",
    var Runtime: String = "",
    var Genre: String = "",
    var Director: String = "",
    var Actors: String = "",
    var Plot: String = "",
    var Poster: String = "",
    var imdbRating: String = "",
    var BoxOffice: String = "",
)