package com.example.appandroid.model

import kotlinx.serialization.Serializable

@Serializable
data class AppResponse(
    val status: String,
    val app: App
)