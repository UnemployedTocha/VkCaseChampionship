package com.example.appandroid.api

import com.example.appandroid.model.App
import retrofit2.http.GET
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

interface ApiService {
    @GET("apps") // эндпоинт на твоём бэке
    suspend fun getApps(): List<App>
}
fun createApiService(): ApiService {
    val contentType = "application/json".toMediaType()
    val json = Json { ignoreUnknownKeys = true } // игнорируем лишние поля

    val retrofit = Retrofit.Builder()
        .baseUrl("https://yourbackend.com/api/") // замени на URL своего бэка
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

    return retrofit.create(ApiService::class.java)
}