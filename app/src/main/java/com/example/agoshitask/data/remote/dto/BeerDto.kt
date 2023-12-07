package com.example.agoshitask.data.remote.dto

import com.example.agoshitask.domain.model.Beer

data class BeerDto(
    val id: Int,
    val name: String,
    val tagline: String,
    val description: String,
    val first_brewed: String?,
    val image_url: String?
)

fun BeerDto.toBeer(): Beer {
    return Beer(
        id = id,
        name = name,
        tagline = tagline,
        description = description,
        firstBrewed = first_brewed,
        imageUrl = image_url
    )
}