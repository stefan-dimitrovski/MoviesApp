package com.stefan.lab3.ui.search

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stefan.lab3.data.movie.retrofit.MovieApiProvider
import com.stefan.lab3.data.movie.room.AppDatabase
import com.stefan.lab3.domain.movie.MovieRepository

class ViewModelProviderFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor(
                MovieRepository::class.java,
            )
            .newInstance(
                MovieRepository(
                    AppDatabase.getDatabase(context).movieDao(),
                    MovieApiProvider.getMovieApi()!!
                )
            )
    }
}