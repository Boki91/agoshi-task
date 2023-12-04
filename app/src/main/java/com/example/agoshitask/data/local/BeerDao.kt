package com.example.agoshitask.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface BeerDao {
    @Upsert
    suspend fun upsertAll(beers: List<BeerEntity>)

    @Query("SELECT * FROM beerentity")
    fun getAllBeers(): Flow<List<BeerEntity>>

    @Query("DELETE FROM beerentity")
    suspend fun clearAll()
}