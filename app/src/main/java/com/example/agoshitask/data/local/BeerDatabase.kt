package com.example.agoshitask.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [BeerEntity::class],
    version = 2,
    exportSchema = false
)
abstract class BeerDatabase : RoomDatabase() {
    abstract val dao: BeerDao

    companion object {
        @Volatile
        private var INSTANCE: BeerDatabase? = null

        fun getInstance(context: Context): BeerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BeerDatabase::class.java,
                    "beer_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}