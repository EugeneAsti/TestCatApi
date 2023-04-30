package ru.aeyu.catapitestapp.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavoriteReq(
    @SerialName("image_id")
    val imageId: String,
    @SerialName("sub_id")
    val subId: String
)
