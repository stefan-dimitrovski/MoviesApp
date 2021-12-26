package com.stefan.lab3.data.movie.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.stefan.lab3.domain.movie.model.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "movie.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}