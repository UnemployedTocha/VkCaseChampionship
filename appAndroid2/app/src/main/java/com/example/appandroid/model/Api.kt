package com.example.appandroid.model


import kotlinx.serialization.Serializable

@Serializable
data class Categories(
    val id: Int,
    val name:  String
)

@Serializable
data class Screenshot(
    val id: Int,
    val url: String
)

@Serializable
data class App(
    val id: Int,
    val name: String,
    val icon_url: String,
    val short_description: String,
    val full_description: String,
    val category_id: Long,
    val developer: String,
    val age_rating: String,
    val apk_url: String,
    val screenshots: List<Screenshot>
)
