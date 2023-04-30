package ru.aeyu.catapitestapp.data.mappers

import ru.aeyu.catapitestapp.data.local.models.CatLocal
import ru.aeyu.catapitestapp.domain.models.Cat

fun CatLocal.toDomainModel() = Cat(
    breeds = listOf(),
    height = this.height ?: 0,
    id = this.id,
    url = this.url ?: "",
    width = this.width ?: 0,
)
//
//fun Breed.toLocalModel(catId: String) = BreedLocal(
//    catId = catId,
//    adaptability = this.adaptability,
//    affectionLevel = this.affectionLevel,
//    altNames = this.altNames,
//    cfaUrl = this.cfaUrl,
//    childFriendly = this.childFriendly,
//    countryCode = this.countryCode,
//    countryCodes = this.countryCodes,
//    description = this.description,
//    dogFriendly = this.dogFriendly,
//    energyLevel = this.energyLevel,
//    experimental = this.experimental,
//    grooming = this.grooming,
//    hairless = this.hairless,
//    healthIssues = this.healthIssues,
//    hypoallergenic = this.hypoallergenic,
//    id = this.id,
//    indoor = this.indoor,
//    intelligence = this.intelligence,
//    lap = this.lap,
//    lifeSpan = this.lifeSpan,
//    name = this.name,
//    natural = this.natural,
//    origin = this.origin,
//    rare = this.rare,
//    referenceImageId = this.referenceImageId,
//    rex = this.rex,
//    sheddingLevel = this.sheddingLevel,
//    shortLegs = this.shortLegs,
//    socialNeeds = this.socialNeeds,
//    strangerFriendly = this.strangerFriendly,
//    suppressedTail = this.suppressedTail,
//    temperament = this.temperament,
//    vcaHospitalsUrl = this.vcaHospitalsUrl,
//    vetStreetUrl = this.vetStreetUrl,
//    vocalisation = this.vocalisation,
//    weightImperial = this.weight.imperial,
//    weightMetric = this.weight.metric,
//    wikipediaUrl = this.wikipediaUrl
//)