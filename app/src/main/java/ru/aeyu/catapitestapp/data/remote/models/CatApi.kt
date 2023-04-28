package ru.aeyu.catapitestapp.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class CatApi(
    val breeds: List<BreedApi>?,
    val height: Int?,
    val id: String?,
    val url: String?,
    val width: Int?
)