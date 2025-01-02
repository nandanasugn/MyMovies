package com.nandana.mymovies.movies.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/{category}")
    suspend fun getMovieList(
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("apiKey") api: String = API_KEY
    ): com.nandana.mymovies.movies.data.remote.response.MovieListDto

    companion object{
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhYTI3MzQ2YWMwZGZlMjhhODM5Mjk1ZjY4Zjg1M2NjMyIsIm5iZiI6MTUyOTY0ODI1My43ODQsInN1YiI6IjViMmM5NDdkMGUwYTI2Mjg1MDAwMTRlNSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.h7GltR355zQgGUTrhQ3kvt1558fctYSbQcO9Ug5E9-0"

    }
}