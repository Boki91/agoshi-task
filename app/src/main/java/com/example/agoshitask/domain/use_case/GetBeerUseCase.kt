package com.example.agoshitask.domain.use_case

import com.example.agoshitask.common.Resource
import com.example.agoshitask.data.mappers.toBeer
import com.example.agoshitask.data.remote.dto.toBeer
import com.example.agoshitask.domain.model.Beer
import com.example.agoshitask.domain.repository.BeerRepository
import kotlinx.coroutines.flow.Flow
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
                repository.refreshBeers()

                val beers = repository.getRemoteBeers().map { it.toBeer() }
                emit(Resource.Success(beers))
            } else {
                repository.getLocalBeers().collect { localBeers ->
                    emit(Resource.Success(localBeers.map { it.toBeer() }))
                }
            }
        } catch (e: Exception) {
            val localBeers = repository.getLocalBeers().firstOrNull()
            emit(Resource.Success(localBeers?.map { it.toBeer() } ?: emptyList()))
        }
    }
}