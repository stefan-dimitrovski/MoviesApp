package com.stefan.lab3.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stefan.lab3.domain.movie.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActivityViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun deleteMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.deleteAllMovies()
        }
    }
}