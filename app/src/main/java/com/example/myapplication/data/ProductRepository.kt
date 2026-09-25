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

    suspend fun getProducts(skip: Int=0,limit: Int=20): ProductResponse {
        return api.getProducts(
            limit = limit,
            skip = skip
        )
    }
}