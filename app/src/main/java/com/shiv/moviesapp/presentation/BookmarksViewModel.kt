package com.shiv.moviesapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shiv.moviesapp.data.model.Movie
import com.shiv.moviesapp.domain.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    val bookmarkedMovies: StateFlow<List<Movie>> = repository.getBookmarkedMovies()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun removeBookmark(id: Int) {
        viewModelScope.launch {
            repository.toggleBookmark(id, false)
        }
    }
}

