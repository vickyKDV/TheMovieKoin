package com.vickykdv.themoviekoin.di

import androidx.paging.PagedList
import com.vickykdv.themoviekoin.data.repository.LocalRepo
import com.vickykdv.themoviekoin.data.repository.RemoteRepo
import org.koin.dsl.module

val repoModule = module {
    single { RemoteRepo(get(),get(),get()) }
    single { LocalRepo(get(),get()) }
}