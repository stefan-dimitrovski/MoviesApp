package com.stefan.lab3.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stefan.lab3.database.dao.MovieDao
import com.stefan.lab3.models.Movie

@Database(entities = [Movie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): MovieDao

    private lateinit var instance: AppDatabase

    fun getInstance(context: Context): AppDatabase {
        if (instance == null) {
            instance = createInstance(context)
        }
        return instance
    }

    private fun createInstance(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "movie.db"
        ).build()
    }

}