package com.nandana.mymovies.movies.domain.respository

import com.nandana.mymovies.movies.domain.model.Movie
import com.nandana.mymovies.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {
    suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>>

    suspend fun getMovie(id: Int): Flow<Resource<Movie>>
}