package com.vickykdv.themoviekoin.data.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.vickykdv.themoviekoin.data.source.MovieDataSource
import com.vickykdv.themoviekoin.data.state.MovieState
import com.vickykdv.themoviekoin.model.DataMovie

class MovieDataFactory(
    private val movieDataSource: MovieDataSource
):DataSource.Factory<Int,DataMovie>() {


    lateinit var liveData: MutableLiveData<MovieState>

    override fun create(): DataSource<Int, DataMovie> {
        return movieDataSource.also {
            it.liveData = liveData
        }
    }

}
