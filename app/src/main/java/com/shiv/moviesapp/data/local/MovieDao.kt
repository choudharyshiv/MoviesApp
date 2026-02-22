package com.shiv.moviesapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shiv.moviesapp.data.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies WHERE category = 'trending' ORDER BY popularity DESC")
    fun getTrendingMovies(): Flow<List<Movie>>

    @Query("SELECT * FROM movies WHERE category = 'now_playing' ORDER BY popularity DESC")
    fun getNowPlayingMovies(): Flow<List<Movie>>

    @Query("SELECT * FROM movies WHERE isBookmarked = 1")
    fun getBookmarkedMovies(): Flow<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Query("UPDATE movies SET isBookmarked = :isBookmarked WHERE id = :id")
    suspend fun setBookmark(id: Int, isBookmarked: Boolean)

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getMovieById(id: Int): Movie?

    @Query("SELECT * FROM movies WHERE title LIKE '%' || :query || '%' OR overview LIKE '%' || :query || '%'")
    fun searchMovies(query: String): Flow<List<Movie>>
}
