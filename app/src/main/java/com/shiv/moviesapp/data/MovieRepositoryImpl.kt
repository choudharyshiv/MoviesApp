package com.shiv.moviesapp.data

import com.shiv.moviesapp.data.local.MovieDao
import com.shiv.moviesapp.data.model.Movie
import com.shiv.moviesapp.data.remote.MovieApiService
import com.shiv.moviesapp.domain.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val apiService: MovieApiService
) : MovieRepository {

    override fun getTrendingMovies(): Flow<List<Movie>> = movieDao.getTrendingMovies()

    override fun getNowPlayingMovies(): Flow<List<Movie>> = movieDao.getNowPlayingMovies()

    override fun getBookmarkedMovies(): Flow<List<Movie>> = movieDao.getBookmarkedMovies()

    override fun searchMovies(query: String): Flow<List<Movie>> = movieDao.searchMovies(query)

    override suspend fun fetchAndCacheTrending() {
        try {
            val response = apiService.getTrendingMovies()
            val movies = response.results.map { it.copy(category = "trending") }
            movieDao.insertMovies(movies)
        } catch (e: Exception) {
            // silently fail — offline mode will show cached data
        }
    }

    override suspend fun fetchAndCacheNowPlaying() {
        try {
            val response = apiService.getNowPlayingMovies()
            val movies = response.results.map { it.copy(category = "now_playing") }
            movieDao.insertMovies(movies)
        } catch (e: Exception) {
            // silently fail — offline mode will show cached data
        }
    }

    override suspend fun getMovieDetail(id: Int): Movie? {
        val cached = movieDao.getMovieById(id)
        if (cached != null) return cached
        return try {
            val remote = apiService.getMovieDetail(id)
            movieDao.insertMovie(remote)
            remote
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun toggleBookmark(id: Int, isBookmarked: Boolean) {
        movieDao.setBookmark(id, isBookmarked)
    }
}
