package com.stefan.lab3.data.movie.retrofit

import com.stefan.lab3.domain.movie.model.Movie
import com.stefan.lab3.env.Env
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("?apikey=${Env.API_KEY}&")
    fun getMovieByTitle(@Query("t") title: String): Call<Movie>
}