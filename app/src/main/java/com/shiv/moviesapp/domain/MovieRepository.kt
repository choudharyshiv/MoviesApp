package com.shiv.moviesapp.domain
import com.shiv.moviesapp.data.model.Movie
import kotlinx.coroutines.flow.Flow
interface MovieRepository {
    fun getTrendingMovies(): Flow<List<Movie>>
    fun getNowPlayingMovies(): Flow<List<Movie>>
    fun getBookmarkedMovies(): Flow<List<Movie>>
    fun searchMovies(query: String): Flow<List<Movie>>
    suspend fun fetchAndCacheTrending()
    suspend fun fetchAndCacheNowPlaying()
    suspend fun getMovieDetail(id: Int): Movie?
    suspend fun toggleBookmark(id: Int, isBookmarked: Boolean)
}
