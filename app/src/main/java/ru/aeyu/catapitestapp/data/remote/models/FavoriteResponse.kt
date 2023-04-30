package ru.aeyu.catapitestapp.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class FavoriteResponse(
    val message: String? = "",
    val id: Int? = 0
)