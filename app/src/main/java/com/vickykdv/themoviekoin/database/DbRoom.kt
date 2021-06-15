package com.vickykdv.themoviekoin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vickykdv.themoviekoin.BuildConfig

@Database(
    entities = [
        MovieEntity::class,
    ],
    version = BuildConfig.VERSION_CODE
)

abstract class DbRoom : RoomDatabase() {

    abstract fun movie() : MovieDao

    companion object {

        @Volatile private var instance : DbRoom? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            DbRoom::class.java,
            BuildConfig.APPLICATION_ID
        )
            .fallbackToDestructiveMigration()
            .build()

    }
}