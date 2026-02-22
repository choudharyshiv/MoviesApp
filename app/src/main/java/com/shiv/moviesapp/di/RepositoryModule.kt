package com.shiv.moviesapp.di

import com.shiv.moviesapp.data.MovieRepositoryImpl
import com.shiv.moviesapp.domain.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideMovieRepository(
        impl: MovieRepositoryImpl
    ): MovieRepository
}