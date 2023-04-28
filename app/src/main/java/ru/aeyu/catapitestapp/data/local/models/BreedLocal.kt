package ru.aeyu.catapitestapp.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CatBreeds", primaryKeys = ["catId", "id"])
data class BreedLocal(
    val catId: String,
    val adaptability: Int?,
    val affectionLevel: Int?,
    val altNames: String?,
    val cfaUrl: String?,
    val childFriendly: Int?,
    val countryCode: String?,
    val countryCodes: String?,
    val description: String?,
    val dogFriendly: Int?,
    val energyLevel: Int?,
    val experimental: Int?,
    val grooming: Int?,
    val hairless: Int?,
    val healthIssues: Int?,
    val hypoallergenic: Int?,
    val id: String,
    val indoor: Int?,
    val intelligence: Int?,
    val lap: Int?,
    val lifeSpan: String?,
    val name: String?,
    val natural: Int?,
    val origin: String?,
    val rare: Int?,
    val referenceImageId: String?,
    val rex: Int?,
    val sheddingLevel: Int?,
    val shortLegs: Int?,
    val socialNeeds: Int?,
    val strangerFriendly: Int?,
    val suppressedTail: Int?,
    val temperament: String?,
    val vcaHospitalsUrl: String?,
    val vetStreetUrl: String?,
    val vocalisation: Int?,
    val weightImperial: String?,
    val weightMetric: String?,
    val wikipediaUrl: String
)