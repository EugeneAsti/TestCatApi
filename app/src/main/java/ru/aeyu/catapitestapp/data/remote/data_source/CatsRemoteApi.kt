package ru.aeyu.catapitestapp.data.remote.data_source

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.aeyu.catapitestapp.data.remote.models.CatApi

interface CatsRemoteApi {
    @GET("/v1/images/search")
    suspend fun getCats(
        @Query("format") format: String? = "json",
        @Query("limit") limit: Int,
        @Query("breed_ids") breedId: String?,
    ) : List<CatApi>

    @GET("/v1/images/{image_id}")
    suspend fun getCat(
        @Path("image_id") imageId: String
    ): CatApi

}