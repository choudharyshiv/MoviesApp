package com.shiv.moviesapp.data.remote

import com.shiv.moviesapp.data.model.Movie
import com.shiv.moviesapp.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("trending/movie/week")
    suspend fun getTrendingMovies(): MovieResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): MovieResponse

    @GET("movie/{id}")
    suspend fun getMovieDetail(@Path("id") id: Int): Movie

    @GET("search/movie")
    suspend fun searchMovies(@Query("query") query: String): MovieResponse
}
