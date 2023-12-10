package com.example.agoshitask.domain.repository

import com.example.agoshitask.data.local.BeerEntity
import com.example.agoshitask.data.remote.dto.BeerDto
import kotlinx.coroutines.flow.Flow

interface BeerRepository {
    suspend fun refreshBeers(remoteBeers: List<BeerDto>)
    suspend fun getLocalBeers(): Flow<List<BeerEntity>>
    suspend fun getRemoteBeers(): List<BeerDto>
    suspend fun isDataAvailable(): Boolean
}