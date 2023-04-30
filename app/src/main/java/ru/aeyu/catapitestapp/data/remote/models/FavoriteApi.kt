package ru.aeyu.catapitestapp.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavoriteApi(
    @SerialName("created_at")
    val createdAt: String,
    val id: Int,
    val image: ImageApi,
    @SerialName("image_id")
    val imageId: String,
    @SerialName("sub_id")
    val subId: String,
    @SerialName("user_id")
    val userId: String
)