package com.vickykdv.themoviekoin.di

import com.vickykdv.themoviekoin.data.source.MovieDataSource
import org.koin.dsl.module

val DataSourceModule = module {
    single { MovieDataSource(get()) }
}