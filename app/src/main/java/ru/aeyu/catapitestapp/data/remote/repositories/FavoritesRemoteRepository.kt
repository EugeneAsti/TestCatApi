package ru.aeyu.catapitestapp.data.remote.repositories

import kotlinx.coroutines.flow.Flow
import ru.aeyu.catapitestapp.data.remote.models.FavoriteReq
import ru.aeyu.catapitestapp.domain.models.Cat

interface FavoritesRemoteRepository {

    suspend fun setFavoriteImage(favoriteReq: FavoriteReq): Flow<Boolean>

    suspend fun getFavoriteCats(userId: String): Flow<List<Cat>>
}