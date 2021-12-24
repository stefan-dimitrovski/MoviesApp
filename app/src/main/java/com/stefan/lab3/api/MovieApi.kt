package com.stefan.lab3.api

import com.stefan.lab3.api.env.Env
import com.stefan.lab3.models.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("?apikey=${Env.API_KEY}&")
    fun getMovieByTitle(@Query("t") title: String): Call<Movie>
}