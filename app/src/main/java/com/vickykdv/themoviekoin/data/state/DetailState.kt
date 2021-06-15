package com.vickykdv.themoviekoin.data.state

import com.vickykdv.themovie.model.detailmovie.ResponseDetailMovie

sealed class DetailState {
    object Loading : DetailState()
    data class Result(val data : ResponseDetailMovie) : DetailState()
    data class Error(val error : Throwable) : DetailState()
}
