package ru.aeyu.catapitestapp.data.local.repository

import kotlinx.coroutines.flow.Flow
import ru.aeyu.catapitestapp.data.local.models.CatLocal
import ru.aeyu.catapitestapp.domain.models.Cat

interface FavoriteCatsLocalRepository {

    suspend fun saveFavoriteCat(catLocal: CatLocal)

    suspend fun saveFavoriteCats(catLocal: List<CatLocal>)

    suspend fun getFavoriteCats(): Flow<List<Cat>>
}