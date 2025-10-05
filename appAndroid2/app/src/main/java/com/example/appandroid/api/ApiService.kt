package com.example.appandroid.api

import com.example.appandroid.model.AppResponse
import com.example.appandroid.model.Categories
import retrofit2.http.GET
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

interface ApiService {
    @GET("app/457")
    suspend fun getApp(): AppResponse
//
//    @GET("categories")
//    suspend fun getCategories(): List<Categories>
}

fun createApiService(): ApiService {
    val contentType = "application/json".toMediaType()
    val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.137.1:8098/")
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

    return retrofit.create(ApiService::class.java)
}
