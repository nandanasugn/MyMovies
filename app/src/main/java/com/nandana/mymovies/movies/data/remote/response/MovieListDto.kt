package com.nandana.mymovies.movies.data.remote.response

data class MovieListDto(
    val dates: Dates,
    val page: Int,
    val results: List<MovieDto>,
    val total_pages: Int,
    val total_results: Int
)