package ru.aeyu.catapitestapp.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class WeightApi(
    val imperial: String?,
    val metric: String?
)