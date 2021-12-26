package com.stefan.lab3.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stefan.lab3.domain.movie.MovieRepository
import com.stefan.lab3.domain.movie.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FirstFragmentViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private var moviesMutableLiveData: MutableLiveData<List<Movie>> = MutableLiveData<List<Movie>>()

    init {
        initializeData()
    }

    private fun initializeData() {
        viewModelScope.launch(Dispatchers.IO) {
            moviesMutableLiveData.postValue(movieRepository.getAllMovies())
        }
    }

    fun search(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.searchMovieByTitle(title)
            val movies = movieRepository.getAllMovies()
            moviesMutableLiveData.postValue(movies)
        }
    }

    fun getMovieMutableLiveData(): MutableLiveData<List<Movie>> {
        return moviesMutableLiveData
    }
}