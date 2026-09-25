package com.example.myapplication.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.productcatalogapp.ui.ProductItem
import com.example.productcatalogapp.ui.ProductUiState
import com.example.productcatalogapp.ui.ProductViewModel

@Composable
fun ProductListScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Trigger the initial load once when the screen first appears
    LaunchedEffect(Unit) {
        viewModel.loadProducts()
    }

    when (val state = uiState) {
        is ProductUiState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is ProductUiState.Empty -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No products found",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        is ProductUiState.Error -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = state.message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        is ProductUiState.Success -> {
            val listState = rememberLazyListState()
            val lastVisibleIndex by remember(state.products.size){
                derivedStateOf{
                    listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
                }
            }

            LaunchedEffect(lastVisibleIndex, state.endReached, state.products.size){
                if(!state.endReached && lastVisibleIndex >= state.products.size -5){
                    viewModel.loadMoreProducts()
                }
            }

            LazyColumn(
                state = listState,
                modifier = modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp)
            ) {
                items(state.products) { product ->
                    ProductItem(products = product)
                }
                if(state.isLoadingMore){
                    item{
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),

                            horizontalArrangement = Arrangement.Center
                        ){
                            CircularProgressIndicator(modifier = Modifier.size(28.dp))
                        }
                    }
                }
            }
        }
    }
}