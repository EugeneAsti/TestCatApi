package ru.aeyu.catapitestapp.data.remote.data_source

import retrofit2.http.GET
import retrofit2.http.Query
import ru.aeyu.catapitestapp.data.remote.models.CatApi

interface TheCatApi {
    @GET("/v1/images/search")
    suspend fun getCats(
        @Query("limit") limit: Int,
        @Query("breed_ids") breedId: String?,
    ) : List<CatApi>

}