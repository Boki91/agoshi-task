package com.example.agoshitask.presentation.beer_list

import com.example.agoshitask.domain.model.Beer

data class BeerListState(
    val isLoading: Boolean = false,
    val beers: List<Beer> = emptyList()
)
