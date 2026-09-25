package com.example.productcatalogapp.data

data class Products(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val rating: Double,
    val thumbnail: String,
    val image: List<String>
)