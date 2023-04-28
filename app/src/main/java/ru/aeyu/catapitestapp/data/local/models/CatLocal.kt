package ru.aeyu.catapitestapp.data.local.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "Cats")
data class CatLocal(
    //@Ignore val breeds: List<BreedLocal?>?,
    val height: Int?,
    @PrimaryKey val id: String,
    val url: String?,
    val width: Int?
)