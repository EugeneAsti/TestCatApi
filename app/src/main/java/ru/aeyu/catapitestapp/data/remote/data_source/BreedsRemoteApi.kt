package ru.aeyu.catapitestapp.data.remote.data_source

import retrofit2.http.GET
import ru.aeyu.catapitestapp.data.remote.models.BreedApi

interface BreedsRemoteApi {

    @GET("/v1/breeds")
    suspend fun getBreeds(): List<BreedApi>

}