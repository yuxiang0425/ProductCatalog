package com.example.productcatalogapp.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class ProductRepository {
    private val api: ProductApi

    init{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
        api = retrofit.create(ProductApi::class.java)
    }

    suspend fun getProducts(): ProductResponse {
        return api.getProducts(
            limit = 20,
            skip = 0
        )
    }
}