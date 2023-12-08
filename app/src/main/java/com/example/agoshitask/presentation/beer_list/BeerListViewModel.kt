package com.example.agoshitask.presentation.beer_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agoshitask.common.Resource
import com.example.agoshitask.domain.use_case.GetBeersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BeerListViewModel @Inject constructor(
    private val getBeersUseCase: GetBeersUseCase
) : ViewModel() {
    private val _state = mutableStateOf(BeerListState())
    val state: State<BeerListState> = _state

    init {
        getBeers()
    }

    private fun getBeers() {
        getBeersUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = BeerListState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = BeerListState(beers = result.data ?: emptyList())
                }
                is Resource.NoInternet -> {
                    _state.value = BeerListState(beers = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = BeerListState(
                        error = result.errorMessage ?: "An unexpected error occurred"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}