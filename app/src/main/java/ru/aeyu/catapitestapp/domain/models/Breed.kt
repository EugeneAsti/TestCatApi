package ru.aeyu.catapitestapp.domain.models

import java.util.Objects

data class Breed(
    val adaptability: Int,
    val affectionLevel: Int,
    val altNames: String,
    val cfaUrl: String,
    val childFriendly: Int,
    val countryCode: String,
    val countryCodes: String,
    val description: String,
    val dogFriendly: Int,
    val energyLevel: Int,
    val experimental: Int,
    val grooming: Int,
    val hairless: Int,
    val healthIssues: Int,
    val hypoallergenic: Int,
    val id: String,
    val indoor: Int,
    val intelligence: Int,
    val lap: Int,
    val lifeSpan: String,
    val name: String,
    val natural: Int,
    val origin: String,
    val rare: Int,
    val referenceImageId: String,
    val rex: Int,
    val sheddingLevel: Int,
    val shortLegs: Int,
    val socialNeeds: Int,
    val strangerFriendly: Int,
    val suppressedTail: Int,
    val temperament: String,
    val vcaHospitalsUrl: String,
    val vetStreetUrl: String,
    val vocalisation: Int,
    val weight: Weight,
    val wikipediaUrl: String
){
    constructor() : this(
        adaptability = 0,
    affectionLevel = 0,
    altNames = "",
    cfaUrl = "",
    childFriendly = 0,
    countryCode = "",
    countryCodes = "",
    description = "",
    dogFriendly = 0,
    energyLevel = 0,
    experimental = 0,
    grooming = 0,
    hairless = 0,
    healthIssues = 0,
    hypoallergenic = 0,
    id = "",
    indoor = 0,
    intelligence = 0,
    lap = 0,
    lifeSpan = "",
    name = "",
    natural = 0,
    origin = "",
    rare = 0,
    referenceImageId = "",
    rex = 0,
    sheddingLevel = 0,
    shortLegs = 0,
    socialNeeds = 0,
    strangerFriendly = 0,
    suppressedTail = 0,
    temperament = "",
    vcaHospitalsUrl = "",
    vetStreetUrl = "",
    vocalisation = 0,
    weight = Weight(),
    wikipediaUrl = ""
    )
    override fun hashCode(): Int {
        return Objects.hash(id, name, altNames)
    }

    override fun equals(other: Any?): Boolean {

        return when {
            (other == null) -> {
                false
            }
            (other === this) -> {
                true
            }
            (other !is Cat) -> {
                false
            }
            else -> {
                other.id == id
            }
        }
    }
}