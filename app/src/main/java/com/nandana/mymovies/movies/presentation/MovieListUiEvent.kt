package com.nandana.mymovies.movies.presentation

sealed interface MovieListUiEvent {
    data class Paginate(val category: String): MovieListUiEvent
    object Navigate: MovieListUiEvent
}