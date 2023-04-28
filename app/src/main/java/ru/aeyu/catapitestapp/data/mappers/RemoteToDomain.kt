package ru.aeyu.catapitestapp.data.mappers

import ru.aeyu.catapitestapp.data.local.models.BreedLocal
import ru.aeyu.catapitestapp.data.local.models.CatLocal
import ru.aeyu.catapitestapp.data.remote.models.BreedApi
import ru.aeyu.catapitestapp.data.remote.models.CatApi
import ru.aeyu.catapitestapp.data.remote.models.WeightApi
import ru.aeyu.catapitestapp.domain.models.Breed
import ru.aeyu.catapitestapp.domain.models.Cat
import ru.aeyu.catapitestapp.domain.models.Weight

fun CatApi.toDomainModel() = Cat(
    breeds = this.breeds?.map {
        it.toDomainModel()
    } ?: listOf(),
    height = this.height ?: 0,
    id = this.id ?: "",
    url = this.url ?: "",
    width = this.width ?: 0,
)

fun BreedApi.toDomainModel() = Breed(
    adaptability = this.adaptability ?: 0,
    affectionLevel = this.affectionLevel ?: 0,
    altNames = this.altNames ?: "",
    cfaUrl = this.cfaUrl ?: "",
    childFriendly = this.childFriendly ?: 0,
    countryCode = this.countryCode ?: "",
    countryCodes = this.countryCodes ?: "",
    description = this.description ?: "",
    dogFriendly = this.dogFriendly ?: 0,
    energyLevel = this.energyLevel ?: 0,
    experimental = this.experimental ?: 0,
    grooming = this.grooming ?: 0,
    hairless = this.hairless ?: 0,
    healthIssues = this.healthIssues ?: 0,
    hypoallergenic = this.hypoallergenic ?: 0,
    id = this.id ?: "",
    indoor = this.indoor ?: 0,
    intelligence = this.intelligence ?: 0,
    lap = this.lap ?: 0,
    lifeSpan = this.lifeSpan ?: "",
    name = this.name ?: "",
    natural = this.natural ?: 0,
    origin = this.origin ?: "",
    rare = this.rare ?: 0,
    referenceImageId = this.referenceImageId ?: "",
    rex = this.rex ?: 0,
    sheddingLevel = this.sheddingLevel ?: 0,
    shortLegs = this.shortLegs ?: 0,
    socialNeeds = this.socialNeeds ?: 0,
    strangerFriendly = this.strangerFriendly ?: 0,
    suppressedTail = this.suppressedTail ?: 0,
    temperament = this.temperament ?: "",
    vcaHospitalsUrl = this.vcaHospitalsUrl ?: "",
    vetStreetUrl = this.vetStreetUrl ?: "",
    vocalisation = this.vocalisation ?: 0,
    weight = this.weight?.toDomainModel() ?: Weight(),
    wikipediaUrl = this.wikipediaUrl ?: ""
)

fun WeightApi.toDomainModel() = Weight(
    imperial = this.imperial ?: "",
    metric = this.metric ?: ""
)

fun Cat.toLocalModel() = CatLocal(
//    breeds = this.breeds.map {
//        it.toLocalModel(this.id)
//    },
    height = this.height,
    id = this.id,
    url = this.url,
    width = this.width,
)

fun Breed.toLocalModel(catId: String) = BreedLocal(
    catId = catId,
    adaptability = this.adaptability,
    affectionLevel = this.affectionLevel,
    altNames = this.altNames,
    cfaUrl = this.cfaUrl,
    childFriendly = this.childFriendly,
    countryCode = this.countryCode,
    countryCodes = this.countryCodes,
    description = this.description,
    dogFriendly = this.dogFriendly,
    energyLevel = this.energyLevel,
    experimental = this.experimental,
    grooming = this.grooming,
    hairless = this.hairless,
    healthIssues = this.healthIssues,
    hypoallergenic = this.hypoallergenic,
    id = this.id,
    indoor = this.indoor,
    intelligence = this.intelligence,
    lap = this.lap,
    lifeSpan = this.lifeSpan,
    name = this.name,
    natural = this.natural,
    origin = this.origin,
    rare = this.rare,
    referenceImageId = this.referenceImageId,
    rex = this.rex,
    sheddingLevel = this.sheddingLevel,
    shortLegs = this.shortLegs,
    socialNeeds = this.socialNeeds,
    strangerFriendly = this.strangerFriendly,
    suppressedTail = this.suppressedTail,
    temperament = this.temperament,
    vcaHospitalsUrl = this.vcaHospitalsUrl,
    vetStreetUrl = this.vetStreetUrl,
    vocalisation = this.vocalisation,
    weightImperial = this.weight.imperial,
    weightMetric = this.weight.metric,
    wikipediaUrl = this.wikipediaUrl
)
