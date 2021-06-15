package com.vickykdv.themoviekoin.ui.favourite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.vickykdv.themoviekoin.data.repository.LocalRepo
import com.vickykdv.themoviekoin.database.MovieEntity

class FavouriteViewModel (
    private val repository: LocalRepo
) : ViewModel() {

    val data : MutableLiveData<PagedList<MovieEntity>> by lazy {
        MutableLiveData<PagedList<MovieEntity>>()
    }

    fun getFavoriteMovie(){
        repository.getFavoriteData(data)
    }
}