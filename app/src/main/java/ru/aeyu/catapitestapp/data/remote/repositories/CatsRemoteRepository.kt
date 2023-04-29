package ru.aeyu.catapitestapp.data.remote.repositories

import kotlinx.coroutines.flow.Flow
import ru.aeyu.catapitestapp.domain.models.Breed
import ru.aeyu.catapitestapp.domain.models.Cat

interface CatsRemoteRepository {
    suspend fun getCats(catsQuantity: Int, breedId: String? = null) : Flow<List<Cat>>
    suspend fun getCat(catImageId: String): Flow<Cat>

    suspend fun getBreeds(): Flow<List<Breed>>
}