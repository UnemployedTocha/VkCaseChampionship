package com.example.appandroid.repository

import com.example.appandroid.api.ApiService
import com.example.appandroid.model.App
import com.example.appandroid.model.AppResponse
import com.example.appandroid.model.Categories

class AppRepository(private val apiService: ApiService) {

    suspend fun getAppData(): App {
        val response = apiService.getApp()
        if (response.status == "Ok") {
            return response.app
        } else {
            throw Exception("API returned error status: ${response.status}")
        }
    }
//
//    suspend fun getCategories(): List<Categories> {
//        return apiService.getCategories()
//    }
}