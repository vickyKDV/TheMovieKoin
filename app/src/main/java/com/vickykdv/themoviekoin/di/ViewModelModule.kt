package com.vickykdv.themoviekoin.di

import com.vickykdv.themovie.ui.detail.DetailViewModel
import com.vickykdv.themoviekoin.ui.main.MainViewModel
import com.vickykdv.themoviekoin.ui.favourite.FavouriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get(),get()) }
    viewModel { FavouriteViewModel(get()) }

}