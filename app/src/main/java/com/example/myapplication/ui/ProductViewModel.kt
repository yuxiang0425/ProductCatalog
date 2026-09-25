package com.example.productcatalogapp.ui

import android.os.ProxyFileDescriptorCallback
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productcatalogapp.data.ProductRepository
import com.example.productcatalogapp.data.Products
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ProductViewModel : ViewModel() {
    private val repository = ProductRepository()
    private val _uiState = MutableStateFlow<ProductUiState>(
        ProductUiState.Loading
    )

    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    fun loadProducts(){
        _uiState.value = ProductUiState.Loading

        viewModelScope.launch{
            try{
                val response = repository.getProducts()
                if(response.products.isEmpty()){
                    _uiState.value = ProductUiState.Empty
                }else{
                    _uiState.value = ProductUiState.Success(response.products)
                }
            }catch(e: Exception){
                _uiState.value = ProductUiState.Error(
                    e.message
                        ?:"Something Went Wrong"
                )
            }
        }
    }
}