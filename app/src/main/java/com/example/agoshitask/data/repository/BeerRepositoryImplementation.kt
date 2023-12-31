package com.example.agoshitask.data.repository

import com.example.agoshitask.data.local.BeerDao
import com.example.agoshitask.data.local.BeerEntity
import com.example.agoshitask.data.mappers.toBeerEntity
import com.example.agoshitask.data.remote.BeerApi
import com.example.agoshitask.data.remote.dto.BeerDto
import com.example.agoshitask.domain.repository.BeerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class BeerRepositoryImplementation @Inject constructor(
    private val api: BeerApi,
    private val beerDao: BeerDao
) : BeerRepository {
    override suspend fun isDataAvailable(): Boolean {
        return if (beerDao.getAllBeers().firstOrNull()?.isNotEmpty() == true) {
            true
        } else {
            try {
                return api.getBeers().isNotEmpty()
            } catch (e: Exception) {
                return false
            }
        }
    }

    override suspend fun getRemoteBeers(): List<BeerDto> {
        return api.getBeers()
    }

    override suspend fun refreshBeers(remoteBeers: List<BeerDto>) {
        val localEntities = remoteBeers.map { it.toBeerEntity() }
        beerDao.clearAll()
        beerDao.upsertAll(localEntities)
    }

    override suspend fun getLocalBeers(): Flow<List<BeerEntity>> {
        return beerDao.getAllBeers()
    }
}