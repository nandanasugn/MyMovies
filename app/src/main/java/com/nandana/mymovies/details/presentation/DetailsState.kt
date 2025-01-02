package com.nandana.mymovies.details.presentation

import com.nandana.mymovies.movies.domain.model.Movie

data class DetailsState(
    val isLoading: Boolean = false,
    val movie: Movie? = null
)