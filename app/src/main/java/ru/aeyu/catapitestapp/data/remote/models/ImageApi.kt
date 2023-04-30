package ru.aeyu.catapitestapp.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class ImageApi(
    val id: String,
    val url: String
)