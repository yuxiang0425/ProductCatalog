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

    private val pagesize = 20
    private var currentSkip = 0
    private var isFetching = false
    fun loadProducts(){
        currentSkip = 0
        isFetching = true
        _uiState.value = ProductUiState.Loading

        viewModelScope.launch{
            try{
                val response = repository.getProducts(skip=0, limit=pagesize)
                currentSkip = response.products.size
                _uiState.value = if(response.products.isEmpty()){
                    ProductUiState.Empty
                }else{
                    ProductUiState.Success(products = response.products,endReached = currentSkip>=response.total)
                }
            }catch(e: Exception){
                _uiState.value = ProductUiState.Error(
                    e.message ?:"Something Went Wrong"
                )
            }finally{
                isFetching = false
            }
        }
    }

    fun loadMoreProducts(){
        val current = _uiState.value
        if(current !is ProductUiState.Success) return
        if(current.endReached || current.isLoadingMore || isFetching) return
        isFetching = true

        _uiState.value = current.copy(isLoadingMore = true)

        viewModelScope.launch{
            try{
                val response = repository.getProducts(skip=currentSkip, limit = pagesize)
                currentSkip += response.products.size

                _uiState.value = ProductUiState.Success(products = current.products + response.products, isLoadingMore = false, endReached = response.products.isEmpty() || currentSkip >= response.total)
            }catch(e:Exception){
                _uiState.value = current.copy(isLoadingMore = false)
            }finally{
                isFetching = false
            }
        }
    }
}