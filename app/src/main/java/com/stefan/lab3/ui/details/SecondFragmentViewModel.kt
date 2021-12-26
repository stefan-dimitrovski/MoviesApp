package com.stefan.lab3.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stefan.lab3.domain.movie.MovieRepository
import com.stefan.lab3.domain.movie.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SecondFragmentViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    private var movieMutableLiveData: MutableLiveData<Movie> = MutableLiveData<Movie>()

    fun getMovieMutableLiveData(): MutableLiveData<Movie> {
        return movieMutableLiveData
    }

    fun getMovieById(movieId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            movieMutableLiveData.postValue(movieRepository.getMovieById(movieId))
        }
    }
}