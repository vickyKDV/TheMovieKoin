package com.vickykdv.themoviekoin.di

import androidx.paging.PagedList
import com.vickykdv.themoviekoin.database.DbRoom
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single {
        PagedList.Config.Builder()
            .setPageSize(1)
            .setInitialLoadSizeHint(2)
            .setPrefetchDistance(1)
            .setEnablePlaceholders(true)
            .build()
    }

    single { DbRoom(androidContext()) }


}