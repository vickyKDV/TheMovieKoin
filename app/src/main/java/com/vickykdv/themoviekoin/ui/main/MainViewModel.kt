package com.vickykdv.themoviekoin.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.vickykdv.themoviekoin.data.repository.RemoteRepo
import com.vickykdv.themoviekoin.data.state.MovieState
import com.vickykdv.themoviekoin.model.DataMovie

class MainViewModel(private val repository: RemoteRepo):ViewModel(){

    val callback : MutableLiveData<MovieState> by lazy {
        MutableLiveData<MovieState>()
    }

    val data : MutableLiveData<PagedList<DataMovie>> by lazy {
        MutableLiveData<PagedList<DataMovie>>()
    }

    fun getMovieData(){
        repository.getMovieData(callback, data)
    }
}