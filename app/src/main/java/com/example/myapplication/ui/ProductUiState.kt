package com.example.productcatalogapp.ui

import com.example.productcatalogapp.data.Products

sealed interface ProductUiState {
    data object Loading: ProductUiState
    data class Success(
        val products: List<Products>
    ): ProductUiState
    data object Empty : ProductUiState
    data class Error(
        val message: String
    ): ProductUiState
}