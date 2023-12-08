package com.example.agoshitask.domain.use_case

import com.example.agoshitask.common.Resource
import com.example.agoshitask.data.mappers.toBeer
import com.example.agoshitask.domain.model.Beer
import com.example.agoshitask.domain.repository.BeerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetBeersUseCase @Inject constructor(
    private val repository: BeerRepository
) {
    operator fun invoke(): Flow<Resource<List<Beer>>> = flow {
        try {
            emit(Resource.Loading())
            val isRemoteDataAvailable = repository.isRemoteDataAvailable()

            if (isRemoteDataAvailable) {
                val remoteBeers = repository.getRemoteBeers()
                repository.refreshBeers(remoteBeers)

                val localBeers = repository.getLocalBeers().first()
                emit(Resource.Success(localBeers.map { it.toBeer() }))
            } else {
                val localBeers = repository.getLocalBeers().first()

                if (localBeers.isNotEmpty()) {
                    emit(Resource.Success(localBeers.map { it.toBeer() }))
                } else {
                    emit(Resource.Error("No data available. Check your internet connection"))
                }
            }
        } catch (e: Exception) {
            val localBeers = repository.getLocalBeers().firstOrNull()
            emit(Resource.Success(localBeers?.map { it.toBeer() } ?: emptyList()))
        }
    }
}