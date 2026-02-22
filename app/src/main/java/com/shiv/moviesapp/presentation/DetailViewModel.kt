package com.shiv.moviesapp.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shiv.moviesapp.data.model.Movie
import com.shiv.moviesapp.domain.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MovieRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId: Int = savedStateHandle.get<Int>("movieId") ?: 0

    private val _movie = MutableStateFlow<Movie?>(null)
    val movie: StateFlow<Movie?> = _movie

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadMovie()
    }

    private fun loadMovie() {
        viewModelScope.launch {
            _isLoading.value = true
            _movie.value = repository.getMovieDetail(movieId)
            _isLoading.value = false
        }
    }

    fun toggleBookmark() {
        val current = _movie.value ?: return
        viewModelScope.launch {
            val newState = !current.isBookmarked
            repository.toggleBookmark(current.id, newState)
            _movie.value = current.copy(isBookmarked = newState)
        }
    }
}

