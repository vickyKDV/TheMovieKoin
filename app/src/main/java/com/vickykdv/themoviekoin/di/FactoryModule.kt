package com.vickykdv.themoviekoin.di

import com.vickykdv.themoviekoin.data.factory.Factory
import com.vickykdv.themoviekoin.data.factory.MovieDataFactory
import org.koin.dsl.module

val FactoryModule = module {
    factory { Factory(get()) }
    factory { MovieDataFactory(get()) }
}