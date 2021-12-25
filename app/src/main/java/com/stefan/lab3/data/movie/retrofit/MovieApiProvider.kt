package com.stefan.lab3.data.movie.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieApiProvider {

    companion object {
        private var movieApi: MovieApi? = null

        fun getMovieApi(): MovieApi? {

            if (movieApi == null) {
                movieApi = Retrofit.Builder()
                    .baseUrl("http://www.omdbapi.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(MovieApi::class.java)
            }

            return movieApi
        }
    }

}