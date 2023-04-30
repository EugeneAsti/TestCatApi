package ru.aeyu.catapitestapp.data.mappers

import ru.aeyu.catapitestapp.data.remote.models.BreedApi
import ru.aeyu.catapitestapp.data.remote.models.CatApi
import ru.aeyu.catapitestapp.data.remote.models.FavoriteApi
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

fun FavoriteApi.toCatApi(): CatApi = CatApi(
    id = this.image.id,
    url = this.image.url,
    breeds = null,
    height = null,
    width = null
)