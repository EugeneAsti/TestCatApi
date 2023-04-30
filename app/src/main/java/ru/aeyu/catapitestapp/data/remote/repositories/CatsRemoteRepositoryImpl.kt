package ru.aeyu.catapitestapp.data.remote.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import ru.aeyu.catapitestapp.data.mappers.toDomainModel
import ru.aeyu.catapitestapp.data.remote.data_source.CatsRemoteApi
import ru.aeyu.catapitestapp.domain.models.Cat

class CatsRemoteRepositoryImpl(private val catsRemoteApi: CatsRemoteApi)
    : CatsRemoteRepository {
    override suspend fun getCats(catsQuantity: Int, breedId: String?): Flow<List<Cat>> =
        flowOf(catsRemoteApi.getCats(limit = catsQuantity, breedId = breedId)).map { item ->
            item.map {
                it.toDomainModel()
            }
        }

    override suspend fun getCat(catImageId: String): Flow<Cat> =
        flowOf(catsRemoteApi.getCat(catImageId)).map { item ->
            item.toDomainModel()
        }

}