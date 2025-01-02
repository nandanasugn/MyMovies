package com.nandana.mymovies.movies.data.repository

import coil.network.HttpException
import com.nandana.mymovies.data.local.movie.MovieDatabase
import com.nandana.mymovies.data.mapper.toMovie
import com.nandana.mymovies.data.mapper.toMovieEntity
import com.nandana.mymovies.data.remote.MovieApi
import com.nandana.mymovies.movies.domain.model.Movie
import com.nandana.mymovies.movies.domain.respository.MovieListRepository
import com.nandana.mymovies.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

class MovieListRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
) : MovieListRepository {
    override suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))
            val localMovieList = movieDatabase.movieDao.getMovieListByCategory(category)
            val shouldLoadLocalMovie = localMovieList.isNotEmpty()&& !forceFetchFromRemote

            if (shouldLoadLocalMovie){
                emit(Resource.Success(
                    data = localMovieList.map {
                        movieEntity -> movieEntity.toMovie(category)
                    }
                ))

                emit(Resource.Loading(false))
                return@flow
            }

            val movieListFromApi = try {
                movieApi.getMovieList(category, page)
            } catch (e: IOException){
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies data"))
                return@flow
            } catch (e: HttpException){
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies data"))
                return@flow
            } catch (e: Exception){
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies data"))
                return@flow
            }

            val movieEntities = movieListFromApi.results.let {
                it.map { movieDto ->
                    movieDto.toMovieEntity(category)
                }
            }

            movieDatabase.movieDao.upsertMovieList(movieEntities)

            emit(Resource.Success(
                movieEntities.map { it.toMovie(category) }
            ))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading(true))

            val movieEntity = movieDatabase.movieDao.getMovieById(id)

            if (movieEntity != null){
                emit(
                    Resource.Success(movieEntity.toMovie(movieEntity.category))
                )
                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Error("Error movie data not found"))
            emit(Resource.Loading(false))
        }
    }
}