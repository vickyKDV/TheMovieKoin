package com.vickykdv.themoviekoin.data.state

import com.vickykdv.themoviekoin.model.ResponseMovie

sealed class MovieState {
    object Loading : MovieState()
    data class Result(val data : ResponseMovie) : MovieState()
    data class Error(val error : Throwable) : MovieState()
}
