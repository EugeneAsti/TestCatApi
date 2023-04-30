package ru.aeyu.catapitestapp.data.remote.data_source

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import ru.aeyu.catapitestapp.data.remote.models.BreedApi
import ru.aeyu.catapitestapp.data.remote.models.CatApi
import ru.aeyu.catapitestapp.data.remote.models.FavoriteApi
import ru.aeyu.catapitestapp.data.remote.models.FavoriteReq
import ru.aeyu.catapitestapp.data.remote.models.FavoriteResponse

interface FavoritesRemoteApi {

    @POST("/v1/favourites")
    suspend fun addToFavorite(@Body favoriteReq: FavoriteReq): FavoriteResponse

    @GET("/v1/favourites")
    suspend fun getFavorites(@Query("sub_id") userId: String): List<FavoriteApi>

}