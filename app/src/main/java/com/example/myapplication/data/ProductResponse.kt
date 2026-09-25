package com.example.productcatalogapp.data

data class ProductResponse(
    val products: List<Products>,
    val total: Int,
    val skip: Int,
    val limit: Int
)