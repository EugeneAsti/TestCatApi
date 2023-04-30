package ru.aeyu.catapitestapp.data.remote.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import ru.aeyu.catapitestapp.data.mappers.toCatApi
import ru.aeyu.catapitestapp.data.mappers.toDomainModel
import ru.aeyu.catapitestapp.data.remote.data_source.FavoritesRemoteApi
import ru.aeyu.catapitestapp.data.remote.models.FavoriteReq
import ru.aeyu.catapitestapp.domain.models.Cat
import java.lang.Exception
import java.util.Locale

class FavoritesRemoteRepositoryImpl(
    private val favoritesRemoteApi: FavoritesRemoteApi
    ): FavoritesRemoteRepository {


    override suspend fun setFavoriteImage(favoriteReq: FavoriteReq): Flow<Boolean> =
        flowOf(
            try {
                favoritesRemoteApi.addToFavorite(favoriteReq)
            } catch (ex: Exception){
                null
            }
        ).map { result ->
            if(result?.message != null)
                result.message.lowercase(Locale.getDefault()) == "success"
            else
                false
        }.catch {
            emit(false)
        }

    override suspend fun getFavoriteCats(userId: String): Flow<List<Cat>> =
        flowOf(
            try {
                favoritesRemoteApi.getFavorites(userId)
            } catch (ex: Exception){
                listOf()
            }
        ).map { result ->
            result.map {item ->
                item.toCatApi().toDomainModel()
            }
        }


}