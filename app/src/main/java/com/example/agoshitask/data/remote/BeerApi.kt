package com.example.agoshitask.data.remote

import com.example.agoshitask.data.remote.dto.BeerDto
import retrofit2.http.GET

interface BeerApi {
    @GET("beers")
    suspend fun getBeers(): List<BeerDto>
}